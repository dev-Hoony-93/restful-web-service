- ExceptionResponse
- CustomizedResponseEntityExceptionHandler extends ExceptionResponse

---
이런식으로 만들고 
- CustomizedResponseEntityExceptionHandler안에는 ``@ControllerAdvice``를 이용해서 모든 컨트롤러가 실행될때 저걸 선언해놓은 class안에 bean이 실행되게됨