<img width="1212" height="753" alt="image" src="https://github.com/user-attachments/assets/4fdc684d-2f32-45bd-9243-788012361615" />



# 💅 NailAgent Backend

> 네일샵 AI 예약 챗봇 서비스의 백엔드 레포지토리

NailAgent는 네일샵 사장님을 위한 AI 기반 예약 자동화 서비스입니다.  
고객은 카카오톡 채널에서 AI 챗봇과 대화하는 것만으로 예약을 완료할 수 있고,  
사장님은 별도의 응대 없이 예약 현황을 관리할 수 있습니다.

백엔드는 AI(Agent System)와 Frontend(사장님 관리 대시보드)를 연동하여 예약 생성&조회, 고객 관리, 샵 설정 등의 REST API를 제공합니다.

### 주요 도메인

- **예약 (Bookings):** 고객 예약 생성, 조회, 상태 관리 (pending → confirmed → visited / no_show)
- **고객 (Customers):**  카카오 유저 기반 고객 식별, 노쇼 누적 관리
- **삽 설정 (Shop Config):**  영업시간, 휴무일, 예약금, 정책 등 샵 운영 정보 관리
- **결제 (Payments):**  예약금 결제 및 환불 처리 

---

## 🌿 Git Branch 전략

### 브랜치 종류

| 브랜치 | 용도 |
|--------|------|
| `main` | 배포 가능한 안정 브랜치 |
| `feature/{이슈번호}-{설명}` | 기능 개발용 작업 브랜치 |

### 브랜치 네이밍 예시

```
feature/#12-reservation-create
feature/#17-customer-detail
feature/#20-shop-info
```

### Workflow

```
이슈 생성
→ 이슈 번호 기준으로 feature/* 브랜치 생성
→ 기능 개발 & 테스트
→ PR 생성
→ CodeRabbit 리뷰 확인 후 main에 merge
→ 이슈 close & 브랜치 삭제
```

### 운영 원칙

- `main`에는 직접 커밋하지 않습니다.
- 작업은 기능 단위로 `feature/*` 브랜치에서 진행합니다.
- PR 단위도 기능 단위로 유지합니다.
- 리뷰는 CodeRabbit 기준으로 확인 후 merge 합니다.
- merge 완료 후 이슈를 닫고 브랜치를 삭제합니다.

---

## ✉️ 커밋 

```
#{이슈번호} 타입: 변경 요약

예시: #12 feat: 예약 생성 API 구현
```

### 커밋 타입

| 태그 | 설명 |
|------|------|
| `feat` | 새로운 기능 추가 |
| `fix` | 버그 수정 |
| `hotfix` | 급한 버그/이슈 패치 |
| `refactor` | 코드 리팩토링 (기능 변경 없음) |
| `add` | 부가적인 코드/라이브러리/파일 추가 |
| `del` | 불필요한 코드/파일 삭제 |
| `docs` | 문서 작업 (README, Wiki 등) |
| `chore` | 환경 설정, 빌드 작업 등 기타 잡일 |
| `correct` | 오타, 타입 수정 등 |
| `move` | 코드/파일 위치 이동 |
| `rename` | 파일/변수/함수 이름 변경 |
| `improve` | 성능/UX 개선 |
| `test` | 테스트 코드 작성/수정 |
