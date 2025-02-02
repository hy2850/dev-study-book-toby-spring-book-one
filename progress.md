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
- UserMailService mock 활용해서 격리 테스트