package com.ept.Util;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.api.client.json.jackson2.JacksonFactory;


import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

//public class GoogleSheetServiceUtil {
//	
//	public static Sheets getSheetsService() throws IOException, GeneralSecurityException {
//        GoogleCredentials credentials = GoogleCredentials
//                .fromStream(new FileInputStream("src/main/resources/"))
//                .createScoped(List.of(SheetsScopes.SPREADSHEETS));
//
//        return new Sheets.Builder(
//                GoogleNetHttpTransport.newTrustedTransport(),
//                JacksonFactory.getDefaultInstance(),
//                new HttpCredentialsAdapter(credentials)
//        ).setApplicationName("Task Manager App").build();
//    }
//
//}

public class GoogleSheetServiceUtil {

    public static Sheets getSheetsService(String credentialsFilePath) throws IOException, GeneralSecurityException {
        GoogleCredentials credentials = GoogleCredentials
                .fromStream(new FileInputStream(credentialsFilePath))
                .createScoped(List.of(SheetsScopes.SPREADSHEETS));

        return new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials)
        ).setApplicationName("Task Manager App").build();
    }
}
