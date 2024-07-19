package io.github.chengdb.easy.expression.core.context;

import java.util.HashMap;
import java.util.Map;

/**
 * 表达式执行上下文。
 * <p>包含表达式执行时使用的所有內容。包括：全局変量、局部変量、资源</p>
 * @author chengdabai
 * @since 1.0.0
 */
public class Context {
    /**
     * 默认的资源访问器
     */
    private final static EmptyResourceAccess EMPTY_RESOURCE_ACCESS = new EmptyResourceAccess();
    /**
     * 默认的变量访问器
     */
    private final static EmptyVariableAccess EMPTY_VARIABLE_ACCESS = new EmptyVariableAccess();

    /**
     * 默认的全局变量访问器
     */
    private static GlobalVariableAccess globalVariableAccess = new EmptyGlobalVariableAccess();

    /**
     * 资源存取。
     * <p>资源，是指在Context进入表达式之前设置的对象。</p>
     */
    private ResourceAccess resourceAccess;

    /**
     * 变量访问器
     */
    private VariableAccess variableAccess = EMPTY_VARIABLE_ACCESS;


    private Context(ResourceAccess resourceAccess) {
        this.resourceAccess = resourceAccess;
    }

    /**
     * 设置资源
     *
     * @param name 资源名
     * @param resource 资源
     * @return 原资源值
     */
    public Object setResource(String name, Object resource) {
        return resourceAccess.setResource(name, resource, this);
    }

    /**
     * 获取资源
     *
     * @param name 资源名
     * @return 资源
     */
    public Object getResource(String name) {
        return resourceAccess.getResource(name);
    }

    /**
     * 获取最近的资源
     * @return 资源
     */
    public Object getResource() {
        return resourceAccess.getResource();
    }

    /**
     * 更新资源
     * @param name 资源名
     * @param resource 资源
     * @return 是否更新成功
     */
    public boolean updateResource(String name, Object resource) {
        return resourceAccess.updateResource(name, resource);
    }

    /**
     * 设置变量
     *
     * @param name 变量名
     * @param value 变量值
     * @return 原变量值
     */
    public Object setVariable(String name, Object value) {
        return variableAccess.setVariable(name, value, this);
    }

    /**
     * 获取变量
     *
     * @param name 变量名
     * @return 变量值
     */
    public Object getVariable(String name) {
        return variableAccess.getVariable(name);
    }

    /**
     * 创建一个不存放资源的实例。
     *
     * @return context
     */
    public static Context newNonResourceContext() {
        return new Context(EMPTY_RESOURCE_ACCESS);
    }

    /**
     * 创建一个仅存放一个资源的实例。
     *
     * @param resourceName 资源名
     * @param resource 资源
     * @return context
     */
    public static Context newSingleResourceContext(String resourceName, Object resource) {
        SingleResourceAccess singleResourceAccess = new SingleResourceAccess(resourceName, resource);
        return new Context(singleResourceAccess);
    }

    /**
     * 创建一个存放多个资源的实例。
     *
     * @return context
     */
    public static Context newMultipleResourceContext() {
        return new Context(new MultipleResourceAccess());
    }

    /**
     * 设置全局变量
     *
     * @param variableName 变量名
     * @param variable 变量
     * @return 原变量值
     */
    public static Object setGlobalVariable(String variableName, Object variable) {
        return Context.globalVariableAccess.setVariable(variableName, variable);
    }

    /**
     * 获取全局变量
     *
     * @param variableName 变量名
     * @return 变量
     */
    public static Object getGlobalVariable(String variableName) {
        return Context.globalVariableAccess.getVariable(variableName);
    }

    /**
     * 资源存取
     *
     * @author chengdabai
 * @since 1.0.0
     * @since 2024/3/7
     */
    private interface ResourceAccess {
        /**
         * 向上下文中设置资源
         *
         * @param resourceName 资源名称，不能为null
         * @param resource 资源
         * @param context 上下文
         * @return 原资源值
         */
        Object setResource(String resourceName, Object resource, Context context);

        /**
         * 从上下文中获取资源
         *
         * @param resourceName 资源名称
         * @return 资源
         */
        Object getResource(String resourceName);

        /**
         * 从上下文中快速获取资源
         *
         * @return 资源
         */
        Object getResource();

        /**
         * 更新资源
         * @param name 资源名
         * @param resource 资源
         * @return 是否更新成功
         */
        boolean updateResource(String name, Object resource);

    }

    /**
     * 空资源存取
     */
    private static class EmptyResourceAccess implements ResourceAccess {
        @Override
        public Object setResource(String resourceName, Object resource, Context context) {
            // 将context的resourceAccess赋值一个SingleResourceAccess，并存入要存放的资源
            context.resourceAccess = new SingleResourceAccess(resourceName, resource);
            return null;
        }

        @Override
        public Object getResource(String resourceName) {
            return null;
        }

        @Override
        public Object getResource() {
            return null;
        }

        @Override
        public boolean updateResource(String name, Object resource) {
            return false;
        }
    }

    private static class SingleResourceAccess implements ResourceAccess {
        private final String resourceName;
        private Object resource;

        private SingleResourceAccess(String resourceName, Object resource) {
            this.resourceName = resourceName;
            this.resource = resource;
        }

        @Override
        public Object setResource(String resourceName, Object resource, Context context) {
            if (this.resourceName.equals(resourceName)) {
                Object old = this.resource;
                this.resource = resource;
                return old;
            }
            // 已经存放资源的话，将context.resourceAccess替换为MultipleResourceAccess，并存入已存放的资源和要存放的资源
            MultipleResourceAccess multipleResourceAccess = new MultipleResourceAccess();
            context.resourceAccess = multipleResourceAccess;
            multipleResourceAccess.setResource(this.resourceName, this.resource, context);
            return multipleResourceAccess.setResource(resourceName, resource, context);
        }

        @Override
        public Object getResource(String resourceName) {
            if (this.resourceName == null || !this.resourceName.equals(resourceName)) {
                return null;
            }
            return resource;
        }

        @Override
        public Object getResource() {
            return resource;
        }

        @Override
        public boolean updateResource(String name, Object resource) {
            if (resourceName.equals(name)) {
                this.resource = resource;
                return true;
            }
            return false;
        }
    }

    /**
     * 正常有资源的资源存取
     */
    private static class MultipleResourceAccess implements ResourceAccess {
        private final Map<String, Object> resourceMap = new HashMap<>();
        private Object recurrentResource;

        @Override
        public Object setResource(String resourceName, Object resource, Context context) {
            recurrentResource = resource;
            return resourceMap.put(resourceName, resource);
        }

        @Override
        public Object getResource(String resourceName) {
            if (resourceName == null) {
                return null;
            }
            return recurrentResource = resourceMap.get(resourceName);
        }

        @Override
        public Object getResource() {
            return recurrentResource;
        }

        @Override
        public boolean updateResource(String name, Object resource) {
            if (resourceMap.containsKey(name)) {
                resourceMap.put(name, resource);
                return true;
            }
            return false;
        }

    }


    /**
     * 变量存取
     */
    private interface VariableAccess {
        /**
         * 向上下文中设置变量
         *
         * @param variableName 变量名
         * @param variable 变量
         * @param context 上下文
         * @return 原变量值
         */
        Object setVariable(String variableName, Object variable, Context context);

        /**
         * 从上下文中获取变量
         *
         * @param variableName 变量名
         * @return 变量
         */
        Object getVariable(String variableName);
    }

    private static class EmptyVariableAccess implements VariableAccess {
        @Override
        public Object setVariable(String variableName, Object variable, Context context) {
            context.variableAccess = new SingleVariableAccess(variableName, variable);
            return null;
        }

        @Override
        public Object getVariable(String variableName) {
            return null;
        }
    }

    private static class SingleVariableAccess implements VariableAccess {
        private final String variableName;
        private Object variable;

        private SingleVariableAccess(String variableName, Object variable) {
            this.variableName = variableName;
            this.variable = variable;
        }

        @Override
        public Object setVariable(String variableName, Object variable, Context context) {
            if (this.variableName.equals(variableName)) {
                Object old = this.variable;
                this.variable = variable;
                return old;
            }
            // this.variableName不可能为null。
            MultipleVariableAccess multipleVariableAccess = new MultipleVariableAccess();
            context.variableAccess = multipleVariableAccess;
            multipleVariableAccess.setVariable(this.variableName, this.variable, context);
            return multipleVariableAccess.setVariable(variableName, variable, context);
        }
        @Override
        public Object getVariable(String variableName) {
            if (this.variableName.equals(variableName)) {
                return this.variable;
            }
            return null;
        }
    }

    private static class MultipleVariableAccess implements VariableAccess {
        private final Map<String, Object> variableMap = new HashMap<>();

        @Override
        public Object setVariable(String variableName, Object variable, Context context) {
            return variableMap.put(variableName, variable);
        }

        @Override
        public Object getVariable(String variableName) {
            if (variableName == null) {
                return null;
            }
            return variableMap.get(variableName);
        }
    }


    /**
     * 全局变量存取
     */
    private interface GlobalVariableAccess {
        /**
         * 向全局变量表中设置变量
         *
         * @param variableName 变量名
         * @param variable 变量
         * @return 原变量值
         */
        Object setVariable(String variableName, Object variable);

        /**
         * 从全局变量表中获取变量
         *
         * @param variableName 变量名
         * @return 变量
         */
        Object getVariable(String variableName);
    }

    private static class EmptyGlobalVariableAccess implements GlobalVariableAccess {
        @Override
        public Object setVariable(String variableName, Object variable) {
            Context.globalVariableAccess = new SingleGlobalVariableAccess(variableName, variable);
            return null;
        }
        @Override
        public Object getVariable(String variableName) {
            return null;
        }
    }

    private static class SingleGlobalVariableAccess implements GlobalVariableAccess {
        private final String variableName;
        private Object variable;

        private SingleGlobalVariableAccess(String variableName, Object variable) {
            this.variableName = variableName;
            this.variable = variable;
        }
        @Override
        public Object setVariable(String variableName, Object variable) {
            if (this.variableName.equals(variableName)) {
                Object old = this.variable;
                this.variable = variable;
                return old;
            }
            MultipleGlobalVariableAccess multipleGlobalVariableAccess = new MultipleGlobalVariableAccess();
            Context.globalVariableAccess = multipleGlobalVariableAccess;
            multipleGlobalVariableAccess.setVariable(this.variableName, this.variable);
            return multipleGlobalVariableAccess.setVariable(variableName, variable);
        }

        @Override
        public Object getVariable(String variableName) {
            if (this.variableName == null || !this.variableName.equals(variableName)) {
                return null;
            }
            return variableName;
        }
    }

    private static class MultipleGlobalVariableAccess implements GlobalVariableAccess {
        private final Map<String, Object> variableMap = new HashMap<>();

        @Override
        public Object setVariable(String variableName, Object variable) {
            return variableMap.put(variableName, variable);
        }

        @Override
        public Object getVariable(String variableName) {
            if (variableName == null) {
                return null;
            }
            return variableMap.get(variableName);
        }
    }

}
