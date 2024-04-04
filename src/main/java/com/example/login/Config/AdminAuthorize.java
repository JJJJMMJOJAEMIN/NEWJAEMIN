package com.example.login.Config;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

    @Target({ElementType.METHOD, ElementType.TYPE}) // 이 애너테이션은 이 애너테이션이 적용될 수 있는 대상 유형을 지정합니다.

    @Retention(RetentionPolicy.RUNTIME) // 이 애너테이션은 JVM에서 해당 애너테이션이 런타임에도 유지되며 반영적으로 조회할 수 있다는 것을 나타냅니다.

    @PreAuthorize("hasAnyRole('ADMIN')") // 이 애너테이션은 지정된 권한이 있는 사용자만 해당 메서드 또는 클래스에 액세스할 수 있음을 지정합니다.

    public  @interface AdminAuthorize { // 이 부분은 'AdminAuthorize'라는 사용자 정의 애너테이션의 선언입니다.

    }

    //애너테이션 =  보안 규칙을 정의 이라고 gpt 가 말함
