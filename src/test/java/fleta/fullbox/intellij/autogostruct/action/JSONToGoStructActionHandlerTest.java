package fleta.fullbox.intellij.autogostruct.action;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class JSONToGoStructActionHandlerTest {

    private JSONToGoStructActionHandler handler = new JSONToGoStructActionHandler();

    @Test
    public void shouldGenerateGoStructFromSimpleJson() {
        String json = "{\n" +
                "\t\t\"denied\": false,\n" +
                "\t\t\"deniedReason\": \"NO_CREDIT\",\n" +
                "\t\t\"held\": true,\n" +
                "\t\t\"producedPaper\": true,\n" +
                "\t\t\"archived\": false,\n" +
                "\t\t\"watermarked\": false,\n" +
                "\t\t\"conversionsApplied\": false\n" +
                "\t}";

        Optional<String> goStruct = handler.jsonToGoStruct(json);
        assertEquals("", goStruct.orElse(""));
    }

    @Test
    public void shouldGenerateGoStructFromJsonWithNestArray() {
        String json = "{\n" +
                "\t\t\"denied\": false,\n" +
                "\t\t\"deniedReason\": \"NO_CREDIT\",\n" +
                "\t\t\"foo\": [{\"bar\": \"barvalue1\"}, {\"bar\": \"barvalue2\"}],\n" +
                "\t\t\"held\": true,\n" +
                "\t\t\"producedPaper\": true,\n" +
                "\t\t\"archived\": false,\n" +
                "\t\t\"watermarked\": false,\n" +
                "\t\t\"conversionsApplied\": false\n" +
                "\t}";

        Optional<String> goStruct = handler.jsonToGoStruct(json);
        assertEquals("", goStruct.orElse(""));
    }

    @Test
    public void shouldGenGoStructWithNestedFromJson() throws InterruptedException {
        String json = "{\n" +
                "   \"time\": \"2014-12-23T12:12:54Z\",\n" +
                "   \"jobId\": \"1234\",\n" +
                "   \"status\": {\n" +
                "      \"denied\": false,\n" +
                "      \"deniedReason\": \"NO_CREDIT|...[PrintDeniedReasonEnum]\",\n" +
                "      \"held\": true,\n" +
                "      \"producedPaper\": true,\n" +
                "      \"archived\": false,\n" +
                "      \"watermarked\": false,\n" +
                "      \"conversionsApplied\": false\n" +
                "   },\n" +
                "   \"user\": {\n" +
                "      \"name\": \"bfaraux\",\n" +
                "      \"fullName\": \"Bob Faraux\",\n" +
                "      \"emails\": [\n" +
                "         \"bfaruax@mail.com\",\n" +
                "         \"foo@bar.com\"\n" +
                "      ],\n" +
                "      \"homeDirectory\": \"\",\n" +
                "      \"internal\": false,\n" +
                "      \"department\": \"\",\n" +
                "      \"office\": \"\",\n" +
                "      \"groupMembership\": [\n" +
                "         \"[All Users]\",\n" +
                "         \"Staff\"\n" +
                "      ]\n" +
                "   },\n" +
                "   \"chargedAccount\": {\n" +
                "      \"name\": \"bfaraux\",\n" +
                "      \"personal\": true,\n" +
                "      \"cost\": 0.03,\n" +
                "      \"originalCost\": 0.03,\n" +
                "      \"restricted\": true\n" +
                "   },\n" +
                "   \"printQueue\": {\n" +
                "      \"server\": \"laptop-bob\",\n" +
                "      \"name\": \"printer_level2\",\n" +
                "      \"address\": \"net://10.100.64.62/lp\"\n" +
                "   },\n" +
                "   \"originalPrintQueue\": {\n" +
                "      \"server\": \"laptop-bob\",\n" +
                "      \"name\": \"virtual_printer_level2\",\n" +
                "      \"address\": \"local://virtual_printer_level2/LPT1\"\n" +
                "   },\n" +
                "   \"client\": {\n" +
                "      \"networkAddress\": \"10.0.0.1\",\n" +
                "      \"protocol\": \"STANDARD|WEB_PRINT|EMAIL_PRINTING|GOOGLE_CLOUD_PRINT...[PrintJobProtocol]\"\n" +
                "   },\n" +
                "   \"spool\": {\n" +
                "      \"sizeKb\": 124,\n" +
                "      \"pdl\": \"PCL6\"\n" +
                "   },\n" +
                "   \"info\": {\n" +
                "      \"documentName\": \"A brief history of everything.pdf\",\n" +
                "      \"duplex\": true,\n" +
                "      \"copies\": 1,\n" +
                "      \"totalPages\": 22,\n" +
                "      \"totalSheets\": 11,\n" +
                "      \"colorPages\": 4,\n" +
                "      \"colorPagesEstimated\": false,\n" +
                "      \"comment\": \"\",\n" +
                "      \"invoiced\": true,\n" +
                "      \"mediaSize\": {\n" +
                "         \"heightMM\": 210,\n" +
                "         \"widthMM\": 297,\n" +
                "         \"name\": \"A4\"\n" +
                "      }\n" +
                "   }\n" +
                "}";

        Optional<String> goStruct = handler.jsonToGoStruct(json);

        assertNotNull(goStruct.get());

        System.out.println(goStruct.get());

        Thread.sleep(1000);
    }
}