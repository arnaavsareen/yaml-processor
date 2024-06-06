package com.example.yamlprocessor.service;

import com.example.yamlprocessor.model.ConnectionParameters;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

public class CustomConstructor extends Constructor {

    public CustomConstructor(Class<? extends Object> theRoot, LoaderOptions options) {
        super(theRoot, options);
    }

    @Override
    protected Object constructObject(Node node) {
        if (node.getTag().equals(new Tag("!com.example.yamlprocessor.model.ConnectionParameters"))) {
            node.setType(ConnectionParameters.class);
        }
        return super.constructObject(node);
    }
}
