package com.example.yamlprocessor.service;

import com.example.yamlprocessor.model.ConnectionParameters;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class YamlProcessorService {

    public Map<String, ConnectionParameters> readYamlFile(String filePath) throws IOException {
        LoaderOptions loaderOptions = new LoaderOptions();
        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            Yaml yaml = new Yaml(new CustomConstructor(Map.class, loaderOptions));
            return yaml.load(inputStream);
        }
    }

    public void writeYamlFile(String filePath, Map<String, ConnectionParameters> data) throws IOException {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);

        Representer representer = new CustomRepresenter(options);
        representer.addClassTag(ConnectionParameters.class, Tag.MAP);

        Yaml yaml = new Yaml(representer, options);
        try (FileWriter writer = new FileWriter(filePath)) {
            yaml.dump(data, writer);
        }
    }

    public void createEntry(Map<String, ConnectionParameters> data, String key, ConnectionParameters params) {
        if (data.containsKey(key)) {
            throw new IllegalArgumentException("Key already exists. Use updateEntry to update an existing key.");
        }
        data.put(key, params);
    }

    public void updateEntry(Map<String, ConnectionParameters> data, String key, ConnectionParameters params) {
        if (!data.containsKey(key)) {
            throw new IllegalArgumentException("Key does not exist. Use createEntry to create a new key.");
        }
        data.put(key, params);
    }

    public void deleteEntry(Map<String, ConnectionParameters> data, String key) {
        if (!data.containsKey(key)) {
            throw new IllegalArgumentException("Key does not exist.");
        }
        data.remove(key);
    }

    private static class CustomRepresenter extends Representer {
        public CustomRepresenter(DumperOptions options) {
            super(options);
        }

        @SuppressWarnings("unused")
		protected Node representScalar(Tag tag, Object value, DumperOptions.ScalarStyle style) {
            if (value instanceof Boolean || value instanceof Integer) {
                return new ScalarNode(tag, value.toString(), null, null, DumperOptions.ScalarStyle.PLAIN);
            }
            return new ScalarNode(tag, value.toString(), null, null, DumperOptions.ScalarStyle.DOUBLE_QUOTED);
        }

        @Override
        protected Node representMapping(Tag tag, Map<?, ?> mapping, DumperOptions.FlowStyle flowStyle) {
            List<String> keyOrder = List.of("vsphere_host", "vsphere_user", "vsphere_password", 
                                            "ignore_ssl", "specs_size", "fetch_custom_attributes", 
                                            "fetch_tags", "fetch_alarms", "collect_only");

            Map<Object, Object> newMapping = new LinkedHashMap<>();
            for (String key : keyOrder) {
                if (mapping.containsKey(key)) {
                    newMapping.put(key, mapping.get(key));
                }
            }
            for (Map.Entry<?, ?> entry : mapping.entrySet()) {
                if (!newMapping.containsKey(entry.getKey())) {
                    newMapping.put(entry.getKey(), entry.getValue());
                }
            }
            return super.representMapping(tag, newMapping, flowStyle);
        }
    }
}
