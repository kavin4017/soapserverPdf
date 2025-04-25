package com.example.soap.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.xml.validation.XmlValidator;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.XsdSchemaCollection;

@EnableWs
@Configuration
public class WebServiceConfig {
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean
    public XsdSchema requestsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("schema/request.xsd"));
    }

    @Bean(name = "requests")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema requestsSchema) {
        DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();
        wsdl.setPortTypeName("PdfPort");
        wsdl.setLocationUri("/ws");
        wsdl.setTargetNamespace("http://example.com/soap");
//        wsdl.setSchema(requestsSchema);
        wsdl.setSchemaCollection(schemaCollection());
        return wsdl;
    }

    @Bean
    public XsdSchema requestsNewSchema() {
        return new SimpleXsdSchema(new ClassPathResource("schema/requestNew.xsd"));
    }

    @Bean
    public XsdSchemaCollection schemaCollection(){
        return new XsdSchemaCollection() {
            @Override
            public XsdSchema[] getXsdSchemas() {
                return new XsdSchema[]{requestsSchema(), requestsNewSchema()};
            }

            @Override
            public XmlValidator createValidator() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
