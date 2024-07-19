package io.github.chengdb.easy.expression.function.getter.file;

import io.github.chengdb.easy.expression.core.function.Function;
import io.github.chengdb.easy.expression.parser.function.factory.FunctionClassGetter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author chengdabai
 * @since 1.0.0
 */
public class BasedOnFileFunctionGetter extends FunctionClassGetter {
    public static final String CONFIG_FILE_PATH = "function-list.conf";

    private final Map<String, String> config = new HashMap<>();

    public BasedOnFileFunctionGetter(List<String> extConfigList) {
        loadConfigFile();
        for (String extConfig : extConfigList) {
            String[] nameAndPath = extConfig.split(":");
            config.put(nameAndPath[0].trim(), nameAndPath[1].trim());
        }
    }


    public BasedOnFileFunctionGetter(Map<String, String> extConfig) {
        loadConfigFile();
        config.putAll(extConfig);
    }

    public BasedOnFileFunctionGetter() {
        loadConfigFile();
    }


    private void loadConfigFile() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(BasedOnFileFunctionGetter.class.getClassLoader().getResourceAsStream(CONFIG_FILE_PATH))))) {
            String line;
            while ((line = br.readLine()) != null && (line = line.trim()).length() > 1) {
                String[] nameAndPath = line.trim().split(":");
                config.put(nameAndPath[0].trim(), nameAndPath[1].trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @Override
    public Class<? extends Function<?>> functionClass(String functionName) {
        String classPath = config.get(functionName);
        if (classPath == null) {
            return null;
        }
        try {
            @SuppressWarnings("unchecked")
            Class<? extends Function<?>> clazz = (Class<? extends Function<?>>) Class.forName(classPath);
            return clazz;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
