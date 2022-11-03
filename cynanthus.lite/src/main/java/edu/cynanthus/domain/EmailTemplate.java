package edu.cynanthus.domain;

import edu.cynanthus.bean.Bean;

import javax.validation.constraints.NotEmpty;
import java.util.*;

public class EmailTemplate implements Bean {

    private Map<String, String> headers;

    @NotEmpty(message = "#{NotEmpty.emailTemplate.subject}")
    private String subject;

    @NotEmpty(message = "#{NotEmpty.emailTemplate.subject}")
    private List<String> to;
    private List<String> cc;
    private List<String> bcc;

    @NotEmpty(message = "#{NotEmpty.emailTemplate.body}")
    private String body;

    private List<String> attachments;

    public EmailTemplate(
        Map<String, String> headers,
        String subject,
        List<String> to,
        List<String> cc,
        List<String> bcc,
        String body,
        List<String> attachments
    ) {
        this.headers = headers;
        this.subject = subject;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.body = body;
        this.attachments = attachments;
    }

    public EmailTemplate(String subject, String body) {
        this(
            new TreeMap<>(String::compareTo),
            subject,
            new LinkedList<>(),
            new LinkedList<>(),
            new LinkedList<>(),
            body,
            new LinkedList<>()
        );
    }

    public EmailTemplate() {
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public List<String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    public List<String> getBcc() {
        return bcc;
    }

    public void setBcc(List<String> bcc) {
        this.bcc = bcc;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

    public Email instance(Map<String, ?> values) {
        return new Email(this, values);
    }

    @Override
    public EmailTemplate clone() {
        return new EmailTemplate(
            new TreeMap<>(headers),
            subject,
            new LinkedList<>(to),
            new LinkedList<>(cc),
            new LinkedList<>(bcc),
            body,
            attachments
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailTemplate that = (EmailTemplate) o;
        return Objects.equals(headers, that.headers)
            && Objects.equals(subject, that.subject)
            && Objects.equals(to, that.to)
            && Objects.equals(cc, that.cc)
            && Objects.equals(bcc, that.bcc)
            && Objects.equals(body, that.body)
            && Objects.equals(attachments, that.attachments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, to, cc, bcc, body, attachments);
    }

    @Override
    public String toString() {
        return "{" +
            "headers:" + headers +
            ",subject:'" + subject + '\'' +
            ",to:" + to +
            ",cc:" + cc +
            ",bcc:" + bcc +
            ",body:'" + body + '\'' +
            ",attachments:" + attachments +
            '}';
    }

}
