package com.yrw_.retry.sqel;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Author: rw.yang
 * @DateTime: 2025/1/25
 **/
public class SqELTest {

    public static String generateKeyBySpEL(String sqEl, ProceedingJoinPoint joinPoint) {
        SpelExpressionParser parser = new SpelExpressionParser();
        DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        String[] paramNames = nameDiscoverer.getParameterNames(method);
        Expression expression = parser.parseExpression(sqEl);
        EvaluationContext context = new StandardEvaluationContext();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        return expression.getValue(context).toString();
    }

    public String generateKeyBySpEL(User user, User user02) {
        return "";
    }

    public static void main(String[] args) {
        SpelExpressionParser parser = new SpelExpressionParser();
        DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();
        Class<SqELTest> sqELTestClass = SqELTest.class;
        try {
            Method generateKeyBySpEL = sqELTestClass.getMethod("generateKeyBySpEL", User.class, User.class);
            String[] paramNames = nameDiscoverer.getParameterNames(generateKeyBySpEL);
            Expression expression = parser.parseExpression("#user.names[1]");
            EvaluationContext context = new StandardEvaluationContext();
            User[] users = new User[2];
            users[0] = new User("1", Arrays.asList("11", "111").toArray(new String[0]));
            users[1] = new User("2",Arrays.asList("22", "222").toArray(new String[0]));
            for (int i = 0; i < users.length; i++) {
                context.setVariable(paramNames[i], users[i]);
            }
            String string = expression.getValue(context).toString();
            System.out.println(string);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
