package fleta.fullbox.intellij.autogostruct.action;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JSONToGoStructActionHandlerTest {

    private JSONToGoStructActionHandler handler = new JSONToGoStructActionHandler();

    @Test
    public void shouldGenerateGoStructFromSimpleJson() {
        String json = "{\n" +
                "\t\t\"denied\": false,\n" +
                "\t\t\"deniedReason\": \"NO_CREDIT\",\n" +
                "\t\t\"foo\": [1,2,3,4],\n" +
                "\t\t\"held\": true,\n" +
                "\t\t\"producedPaper\": true,\n" +
                "\t\t\"archived\": false,\n" +
                "\t\t\"watermarked\": false,\n" +
                "\t\t\"conversionsApplied\": false\n" +
                "\t}";

        String actual = handler.jsonToGoStruct(json).orElse("");

        String expect = "type AutoGoStruct struct {\n" +
                "  Denied bool `json:\"denied\"`\n" +
                "  DeniedReason string `json:\"deniedReason\"`\n" +
                "  Foo []int `json:\"foo\"`\n" +
                "  Held bool `json:\"held\"`\n" +
                "  ProducedPaper bool `json:\"producedPaper\"`\n" +
                "  Archived bool `json:\"archived\"`\n" +
                "  Watermarked bool `json:\"watermarked\"`\n" +
                "  ConversionsApplied bool `json:\"conversionsApplied\"`\n" +
                "}";
        assertEquals(expect, actual);
    }

    @Test
    public void shouldGenerateGoStructFromJsonWithNestArray() {
        String json = "{\n" +
                "\t\t\"denied\": false,\n" +
                "\t\t\"deniedReason\": \"NO_CREDIT\",\n" +
                "\t\t\"foo\": [{\"bar\": \"barvalue1\"}, {\"bar\": \"barvalue2\", \"boo\": \"boovalue1\"}],\n" +
                "\t\t\"held\": true,\n" +
                "\t\t\"producedPaper\": true,\n" +
                "\t\t\"archived\": false,\n" +
                "\t\t\"watermarked\": false,\n" +
                "\t\t\"conversionsApplied\": false\n" +
                "\t}";

        String actual = handler.jsonToGoStruct(json).orElse("");
        String expect = "type AutoGoStruct struct {\n" +
                "  Denied bool `json:\"denied\"`\n" +
                "  DeniedReason string `json:\"deniedReason\"`\n" +
                "  Foo []struct {\n" +
                "    Bar string `json:\"bar\"`\n" +
                "    Boo string `json:\"boo\"`\n" +
                "  } `json:\"foo\"`\n" +
                "  Held bool `json:\"held\"`\n" +
                "  ProducedPaper bool `json:\"producedPaper\"`\n" +
                "  Archived bool `json:\"archived\"`\n" +
                "  Watermarked bool `json:\"watermarked\"`\n" +
                "  ConversionsApplied bool `json:\"conversionsApplied\"`\n" +
                "}";

        assertEquals(expect, actual);
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

        String actual = handler.jsonToGoStruct(json).orElse("");

        String expect = "type AutoGoStruct struct {\n" +
                "  Time string `json:\"time\"`\n" +
                "  JobId string `json:\"jobId\"`\n" +
                "  Status struct {\n" +
                "    Denied bool `json:\"denied\"`\n" +
                "    DeniedReason string `json:\"deniedReason\"`\n" +
                "    Held bool `json:\"held\"`\n" +
                "    ProducedPaper bool `json:\"producedPaper\"`\n" +
                "    Archived bool `json:\"archived\"`\n" +
                "    Watermarked bool `json:\"watermarked\"`\n" +
                "    ConversionsApplied bool `json:\"conversionsApplied\"`\n" +
                "  } `json:\"status\"`\n" +
                "  User struct {\n" +
                "    Name string `json:\"name\"`\n" +
                "    FullName string `json:\"fullName\"`\n" +
                "    Emails []string `json:\"emails\"`\n" +
                "    HomeDirectory string `json:\"homeDirectory\"`\n" +
                "    Internal bool `json:\"internal\"`\n" +
                "    Department string `json:\"department\"`\n" +
                "    Office string `json:\"office\"`\n" +
                "    GroupMembership []string `json:\"groupMembership\"`\n" +
                "  } `json:\"user\"`\n" +
                "  ChargedAccount struct {\n" +
                "    Name string `json:\"name\"`\n" +
                "    Personal bool `json:\"personal\"`\n" +
                "    Cost float64 `json:\"cost\"`\n" +
                "    OriginalCost float64 `json:\"originalCost\"`\n" +
                "    Restricted bool `json:\"restricted\"`\n" +
                "  } `json:\"chargedAccount\"`\n" +
                "  PrintQueue struct {\n" +
                "    Server string `json:\"server\"`\n" +
                "    Name string `json:\"name\"`\n" +
                "    Address string `json:\"address\"`\n" +
                "  } `json:\"printQueue\"`\n" +
                "  OriginalPrintQueue struct {\n" +
                "    Server string `json:\"server\"`\n" +
                "    Name string `json:\"name\"`\n" +
                "    Address string `json:\"address\"`\n" +
                "  } `json:\"originalPrintQueue\"`\n" +
                "  Client struct {\n" +
                "    NetworkAddress string `json:\"networkAddress\"`\n" +
                "    Protocol string `json:\"protocol\"`\n" +
                "  } `json:\"client\"`\n" +
                "  Spool struct {\n" +
                "    SizeKb int `json:\"sizeKb\"`\n" +
                "    Pdl string `json:\"pdl\"`\n" +
                "  } `json:\"spool\"`\n" +
                "  Info struct {\n" +
                "    DocumentName string `json:\"documentName\"`\n" +
                "    Duplex bool `json:\"duplex\"`\n" +
                "    Copies int `json:\"copies\"`\n" +
                "    TotalPages int `json:\"totalPages\"`\n" +
                "    TotalSheets int `json:\"totalSheets\"`\n" +
                "    ColorPages int `json:\"colorPages\"`\n" +
                "    ColorPagesEstimated bool `json:\"colorPagesEstimated\"`\n" +
                "    Comment string `json:\"comment\"`\n" +
                "    Invoiced bool `json:\"invoiced\"`\n" +
                "    MediaSize struct {\n" +
                "      HeightMM int `json:\"heightMM\"`\n" +
                "      WidthMM int `json:\"widthMM\"`\n" +
                "      Name string `json:\"name\"`\n" +
                "    } `json:\"mediaSize\"`\n" +
                "  } `json:\"info\"`\n" +
                "}";

        assertEquals(expect, actual);
    }
}