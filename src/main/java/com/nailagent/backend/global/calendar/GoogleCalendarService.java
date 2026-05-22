package com.nailagent.backend.global.calendar;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.nailagent.backend.domain.reservation.entity.Reservation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class GoogleCalendarService {

    private final Calendar calendar;
    private final String calendarId;

    public GoogleCalendarService(
            @Value("${google.calendar.id}") String calendarId,
            @Value("${google.calendar.credentials-path}") String credentialsPath,
            ResourceLoader resourceLoader
    ) throws Exception {
        this.calendarId = calendarId;

        InputStream credentialsStream = resourceLoader.getResource(credentialsPath).getInputStream();
        GoogleCredentials credentials = GoogleCredentials
                .fromStream(credentialsStream)
                .createScoped(List.of(CalendarScopes.CALENDAR));

        this.calendar = new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials)
        ).setApplicationName("NailAgent").build();
    }

    public String createEvent(Reservation reservation) {
        try {
            Event event = buildEvent(reservation);
            Event created = calendar.events().insert(calendarId, event).execute();
            log.info("Google Calendar 이벤트 생성: {}", created.getId());
            return created.getId();
        } catch (IOException e) {
            log.error("Google Calendar 이벤트 생성 실패", e);
            return null;
        }
    }

    public void updateEvent(String eventId, Reservation reservation) {
        try {
            Event event = buildEvent(reservation);
            calendar.events().update(calendarId, eventId, event).execute();
            log.info("Google Calendar 이벤트 수정: {}", eventId);
        } catch (IOException e) {
            log.error("Google Calendar 이벤트 수정 실패: eventId={}", eventId, e);
        }
    }

    public void deleteEvent(String eventId) {
        try {
            calendar.events().delete(calendarId, eventId).execute();
            log.info("Google Calendar 이벤트 삭제: {}", eventId);
        } catch (IOException e) {
            log.error("Google Calendar 이벤트 삭제 실패: eventId={}", eventId, e);
        }
    }

    private Event buildEvent(Reservation reservation) {
        // reserveTime 형식: "17:00-18:30"
        String[] timeRange = reservation.getReserveTime().split("-");
        LocalTime startTime = LocalTime.parse(timeRange[0]);
        LocalTime endTime = LocalTime.parse(timeRange[1]);

        LocalDateTime startDt = reservation.getReserveDate().atTime(startTime);
        LocalDateTime endDt = reservation.getReserveDate().atTime(endTime);

        String summary = String.format("[%s] %s", reservation.getService(), reservation.getName());
        String description = String.format(
                "고객명: %s\n연락처: %s\n서비스: %s\n오프 제거: %s\n담당: %s\n예약금: %s원",
                reservation.getName(),
                reservation.getPhoneNum(),
                reservation.getService(),
                Boolean.TRUE.equals(reservation.getOffRemoval()) ? "O" : "X",
                reservation.getDesigner(),
                reservation.getDepositAmount() != null ? reservation.getDepositAmount() : 0
        );

        return new Event()
                .setSummary(summary)
                .setDescription(description)
                .setStart(new EventDateTime()
                        .setDateTime(toGoogleDateTime(startDt))
                        .setTimeZone("Asia/Seoul"))
                .setEnd(new EventDateTime()
                        .setDateTime(toGoogleDateTime(endDt))
                        .setTimeZone("Asia/Seoul"));
    }

    private com.google.api.client.util.DateTime toGoogleDateTime(LocalDateTime ldt) {
        return new com.google.api.client.util.DateTime(
                Date.from(ldt.atZone(ZoneId.of("Asia/Seoul")).toInstant())
        );
    }
}
