# KotlinTest (Android/Kotlin Template)

Android 앱 개발 시 자주 쓰는 **기본 골격(UI/Navigation/Retrofit/DataBinding)** 을 빠르게 확인할 수 있는 샘플 프로젝트입니다.  
`MainActivity`에서 **프래그먼트 호스트 화면으로 이동**하거나, **Retrofit 예제 호출**을 실행해 동작을 확인할 수 있습니다.

## 주요 기능

- **화면 흐름**
  - `MainActivity`(런처) → `HostFragActivity`(NavHost) → `FirstFragment`(startDestination) 및 하위 프래그먼트 이동
- **Navigation Component**
  - `navigation/nav_host.xml` 기반의 프래그먼트 네비게이션 구성
- **Retrofit 네트워크 샘플**
  - 단건/리스트 예제 엔드포인트 호출 및 응답 모델 바인딩
- **DataBinding**
  - ViewModel 값을 레이아웃에 바인딩(`activity_main.xml` 등)

## 기술 스택

- **Language**: Kotlin
- **Build**: Gradle Wrapper (Gradle 7.5), Android Gradle Plugin 7.4.2
- **AndroidX**: Navigation (`navigation-fragment-ktx`, `navigation-ui-ktx`)
- **Network**: Retrofit 2.9.0 + Gson
- **Image**: Glide 4.15.1
- **UI**: Material Components, ConstraintLayout

## 프로젝트 구조

핵심 코드는 `app/src/main/java/com/example/kotlintemplate` 아래에 있습니다.

- **`ui/`**
  - `ui/activity/MainActivity.kt`: 메인 화면(버튼 클릭으로 화면 이동/네트워크 호출)
  - `ui/activity/HostFragActivity.kt`: 프래그먼트 호스트 화면
  - `ui/fragment/*`: `FirstFragment`, `SecondFragment`, `ThirdFragment`, `ListFragment`
  - `ui/adapter/*`: 리스트 UI용 어댑터
- **`network/`**
  - `network/retrofit/*`: Retrofit 설정/요청 매니저/API 인터페이스
  - `network/model/*`: API 응답 모델
- **`viewmodel/`**
  - 화면 바인딩/상태 관리용 ViewModel
- **`databinding/`**
  - 커스텀 바인딩 어댑터(예: 이미지 URL 바인딩)
- **`base/`**
  - `BaseActivity`, `BaseFragment` 등 공통 베이스
- **`common/` / `util/` / `extension/`**
  - 상수, 유틸, 확장/클릭 리스너 등 공통 기능

리소스는 `app/src/main/res` 아래에 있습니다.

- **Layout**: `activity_main.xml`, `activity_frag_host.xml`, `fragment_*.xml`, `item_photo_list.xml`
- **Navigation Graph**: `navigation/nav_host.xml`

## 실행 방법

### Android Studio

1. Android Studio에서 프로젝트 열기
2. Gradle Sync
3. `app` 실행(Run)

### CLI (Gradle Wrapper)

```bash
./gradlew :app:assembleDebug
```

```bash
./gradlew :app:installDebug
```

## 화면/동작 포인트

- **Main 화면**: `MainActivity`
  - `Fragment 이동` 버튼 → `HostFragActivity`로 이동
  - `Retrofit 통신` 버튼 → 단건 데이터 호출 후 ViewModel 업데이트 → DataBinding으로 UI 반영
- **Fragment 호스트**: `HostFragActivity`
  - `NavHostFragment`가 `@navigation/nav_host`를 사용하며, startDestination은 `FirstFragment`입니다.

## API 테스트 데이터(현재 코드 기준)

- **Base URL**: `https://jsonkeeper.com/b/`
- **Endpoints**
  - 단건: `GET /4I1S`
  - 리스트: `GET /BGEN`

## 참고

- 인터넷 권한을 사용합니다: `android.permission.INTERNET`

---