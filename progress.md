### 20250128 화

- transaction 시작 = DB에 락 걸림, commit 혹은 rollback을 해줘야 락이 풀리고 다른 트랜잭션 시작 가능

### 20250129 수

- Advice는 타겟 정보를 가지지 않는다! -> 싱글톤 등록 가능

### 20250201 토 - Finished Ch6 (p.500 ~ p.555)

- 트랜잭션이라는 부가기능을 핵심로직 코드에서 완전히 분리시키기 위해 다양한 기술 활용 시도

```
메소드 추출, DI 활용한 전략패턴 
→ 데코레이터 패턴 (클라이언트-프록시-타겟 형태로 중간에 끼어서 부가기능 실행하고 타겟에 작업 위임) 
→ 다이내믹 프록시 (프록시 클래스 직접 만들기 귀찮아서, 인터페이스 주면 자동으로 만들어주는 JDK 프록시 활용)
→ 프록시 팩토리 빈 (다이내믹 프록시를 빈으로 자동등록 하기 위해, 어드바이스 & 포인트컷과 템플릿-콜백 패턴 활용헌 타겟 위임)
→ 빈 후처리기 + 클래스 지정 포인트컷으로 자동 프록시 생성 (여러 클래스에 걸쳐서 부가기능 적용)
→ AspectJ expression
→ Aspect Oriented Programming (AOP) : Advice + Pointcut + Joinpoint + Aspect
```

- 인터페이스 기반 스프링 JDK 프록시 vs CGLIB 활용한 바이트코드 조작 프록시 (Aspect)
- '트랜잭션 전파' 공부 (이미 실행중인 트랜잭션에 합류하기 or 새로운 트랜잭션 시작하기)
- 스프링이 제공하는 `TranscationInterceptor` Advice (AOP) 활용해서 타겟 메소드에 트랜잭션 적용
- `@Transactional`도 AOP 기술로 작동한다 (스프링 내장된 포인트컷, 어드바이스 사용)
  - 인터페이스, 클래스, 메소드 레벨에 annotation 붙여서 꼭 필요한 곳에만 정밀하게 트랜잭션 적용 가능
- 선언적 declarative 트랜잭션 vs programmatic 트랜잭션
  - 선언적 : 코드 외부에서 트랜잭션 이미 시작, 코드 자체엔 트랜잭션 코드 없지만 코드는 이미 트랜잭션 내부에서 실행중
  - programmatic : TC에서 TransactionManager 주입받아서 의도적으로 트랜잭션 시작; 트랜잭션 동기화, 롤백 러닝 테스트에 활용
- ⭐️ 동일 클래스 내 다른 메소드 호출하면, 프록시 통해서 호출하는게 아니므로, AOP 기술 사용한 부가기능 적용 안됨
- 새로운 개념을 배우면 러닝테스트를 꼭 만들자! Make learning tests whenever you encounter new terms and features

### 20250202 일 - 간단히 코드보고 Ch5 복습, 스프링 doc 탐색

Ch6 AOP 내용들이 아래 Spring doc에 다 나와있었네
https://docs.spring.io/spring-framework/reference/core/aop.html


Ch5 복습 - 추상화 & 테스트할 sut의 의존성 mocking해서 단위테스트 격리

- `PlatformTransactionManager` 적용, 트랜잭션 동기화 & 추상화
  - 추상화 : https://docs.spring.io/spring-framework/reference/data-access/transaction/strategies.html
  - 선언적 트랜잭션 : https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative.html
  - @Transactional : https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html
- 메일 전송 기능 구체 클래스를 mocking하기 위해, 직접 추상화 도입. 핵심 기능만 인터페이스로 빼서~

<br>

### 20250203 월 - 이전 챕터들 복습

토비 스프링 Ch1

```
책임 = 변경, 수정이 일어날 때 함께 변하는 부분
→ 별도 클래스로 분리

서로 다른 책임을 가진 클래스끼리의 의존성은, 구체 클래스 말고 인터페이스 사용해서 느슨하게 연결 
(한쪽의 구체 사항이 변경되어도, 인터페이스가 바뀐게 아닌 이상 다른쪽은 영향 받지 않도록)

강한 응집성, 느슨한 결합
OCP, DIP
이걸 가능하게 도와주는게 스프링 DI (객체 생성과 런타임 의존관계 설정하는 책임을 스프링 컨테이너가 맡아줌)

이걸 달성하면? 코드 변경과 수정이 훨씬 편하고 자유로워짐.
변경에 영향받는 부분도 적을테고, 그 부분만 수정하고 TC 돌리면 끝.
그게 아니면 repo 여기저기 코드를 다 일일이 찾아서 바꿔야 함.
```

토비 스프링 Ch2

- 유닛 테스트는 필수
- 꼭 TDD 해라 (테스트 = 구현하기 전에 specification 틀을 코드로 작성하는 것, 계획 짜는 것)
- 모르는 기능은 꼭 학습테스트 짜서 공부하기
- 네거티브 테스트를 가장 먼저 만들어라

토비 스프링 Ch3

- 자주 바뀌는 코드(전략, 콜백)와 바뀌지 않고 계속 재사용되는 코드(템플릿)을 분리
- 바뀌지 않는 틀/템플릿에, 바뀌는 코드의 구현체를 DI 함으로써 템플릿 코드 여러 번 재사용 (OCP 달성)
- 전략패턴, 템플릿 콜백 패턴 활용

<br>

토비 스프링 Ch4 - 예외 핸들링

- 예외 catch해서 씹거나, 무지성 throws는 ❌ (예외는 반드시 처리하거나, 로깅해서 개발자에게 알리기)
- 예외 처리 방법 : 여기서 처리 못하면 throws / 예외 전환 or 예외 포장 (주로 checked -> unchecked로 바꿔버리기)
- checked vs unchecked exception (RuntimeException)
  - 💡Kotlin은 unchecked exception 뿐이라, 코드 작업할게 없더라
- example) How Spring translates different DB exception into it's own abstract 'DataAccessException' (unchecked)