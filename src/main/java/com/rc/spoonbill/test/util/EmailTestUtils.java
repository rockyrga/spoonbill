package com.rc.spoonbill.test.util;

import java.io.IOException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;

public class EmailTestUtils {

    private static EmailTestUtils instance;

    private EmailTestUtils() {

    }

    public static EmailTestUtils getInstance() {

        if (instance == null) {
            instance = new EmailTestUtils();
        }

        return instance;
    }

    public String getAttachmentFileName(Part part) throws MessagingException, IOException {

        if (part.isMimeType("multipart/*")) {

            Multipart multipart = (Multipart) part.getContent();
            for (int index = 0; index < multipart.getCount(); index++) {

                BodyPart bodyPart = multipart.getBodyPart(index);
                if (bodyPart.isMimeType("application/octet-stream")) {

                    return bodyPart.getFileName();
                }
            }
        }

        return null;
    }

    public DataSource getAttachmentDataSource(Part part) throws MessagingException, IOException {

        if (part.isMimeType("multipart/*")) {

            Multipart multipart = (Multipart) part.getContent();
            for (int index = 0; index < multipart.getCount(); index++) {

                BodyPart bodyPart = multipart.getBodyPart(index);
                if (bodyPart.isMimeType("application/octet-stream")) {

                    DataHandler dataHandler = bodyPart.getDataHandler();
                    return dataHandler.getDataSource();
                }
            }
        }

        return null;
    }

    public String getContent(Part part) throws MessagingException, IOException {

        if (part.isMimeType("text/*")) {

            String string = (String) part.getContent();
            return string;
        }

        if (part.isMimeType("multipart/alternative")) {

            Multipart multipart = (Multipart) part.getContent();
            String text = null;
            for (int index = 0; index < multipart.getCount(); index++) {

                Part bodyPart = multipart.getBodyPart(index);
                if (bodyPart.isMimeType("text/plain")) {

                    if (text == null) {
                        text = getContent(bodyPart);
                    }
                    continue;

                } else if (bodyPart.isMimeType("text/html")) {

                    String string = getContent(bodyPart);
                    if (string != null) {
                        return string;
                    }

                } else {

                    return getContent(bodyPart);
                }
            }

            return text;

        } else if (part.isMimeType("multipart/*")) {

            Multipart multipart = (Multipart) part.getContent();
            for (int index = 0; index < multipart.getCount(); index++) {

                String string = getContent(multipart.getBodyPart(index));
                if (string != null) {
                    return string;
                }
            }
        }

        return null;
    }
}
