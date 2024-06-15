package pl.webd.dawid124.ioengine.module.expresion;

import javassist.Modifier;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.automation.macro.block.runner.ConditionVariableRunner;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ExpressionProxy<T> {

    public static final String VAR_NOT_EXISTS = "Variable with name [%s] not exists";
    private final Class<T> type;
    private final T orgObj;
    private final Map<String, String> expressions;
    
    public ExpressionProxy(Class<T> type, T orgObj, Map<String, String> expressions) {
        this.type = type;
        this.expressions = expressions;
        this.orgObj = orgObj;
    }

    public T build(AutomationContext context) {
        return (T) createProxy(context);
    }

    private Object createProxy(AutomationContext context) {
        ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(type);
        factory.setFilter(method -> Modifier.isPublic(method.getModifiers()));

        MethodHandler handler = (self, thisMethod, proceed, args) -> {
            String filed = getFieldName(thisMethod);

            String expression = expressions.get(filed.toLowerCase());
            if (expression != null) {
                return getByExpression(context, expression);
            } else {
                return thisMethod.invoke(orgObj, args);
            }
        };

        try {
            return factory.create(new Class<?>[0], new Object[0], handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    private Object getByExpression(AutomationContext context, String expression) {
        String variable = expression.substring(2, expression.length() - 1);

        if (context.getStateService().getVariables().get(variable) != null) {
            return context.getStateService().getVariables().get(variable).getVar().getValue();
        } else {
            String[] variables = variable.split("\\.");
            ConditionVariableRunner conditionVariableRunner = context.getStructureService().fetchConditionVariable(variables[0]);

            if (conditionVariableRunner == null) throw new RuntimeException(String.format(VAR_NOT_EXISTS, variables[0]));

            String secondVariable = null;
            if (variables.length > 1) {
                secondVariable = variables[1];
            }
            return conditionVariableRunner
                    .execute(context, secondVariable, new HashMap<>(), "")
                    .getValue();
        }
    }

    private String getFieldName(Method method) {
        String filed = method.getName();
        if (method.getName().startsWith("get")) {
            filed = filed.substring(3);
        } else if (method.getName().startsWith("is")) {
            filed = filed.substring(2);
        }
        return filed;
    }
}
