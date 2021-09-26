//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dora.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
    prefix = "swagger"
)
public class SwaggerConfig {
    private String basePackage = "com.chinacreator.hdh";
    private String title;
    private String description;
    private String contact;
    private String version;

    public SwaggerConfig() {
    }

    public String getBasePackage() {
        return this.basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String toString() {
        return "SwaggerConfig{basePackage='" + this.basePackage + '\'' + ", title='" + this.title + '\'' + ", description='" + this.description + '\'' + ", contact='" + this.contact + '\'' + ", version='" + this.version + '\'' + '}';
    }
}
