import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ApiJavaAutomationTest {
    final static String firstUrl = "http://dummy.restapiexample.com";
    final static String secondUrl = "https://reqres.in";

    /*
        Hallo ini adalah initial state terkait java rest assured kita.
        Coba run class ini, dan pastikan program berjalan dan menghasilkan
        output "The response status is 200".
        Segera kontak tim Atlas jika menemui kendala!

        Nantikan berbagai latihan dan problem set di kelas nanti
     */

    @Test
    public void firstTrial() {
        Response response = given().baseUri(firstUrl).basePath("/api/v1").contentType(ContentType.JSON)
                .get("/employees");

        int statusCode = response.getStatusCode();
        Assert.assertEquals("Tiger Nixon", response.path("data.employee_name[0]"));
        System.out.println("The response status is " + statusCode);
        System.out.println("First Name Of Employee = " + response.path("data.employee_name[0]"));
    }

    @Test
    public void getResponseBodyWithLog() {
        Response response =
                given().log().all()
                        .baseUri(secondUrl).basePath("/api/").contentType(ContentType.JSON)
                        .param("page", 1)
                        .param("per_page", 3)
                        .get("users");

        Assert.assertEquals("1", response.path("data.id[0]"));
        response.getBody().prettyPrint();
    }

    @Test
    public void getResponseBody() {
        Response response =
                given().baseUri(secondUrl).basePath("/api/").contentType(ContentType.JSON)
                        .param("data.id[0]", 1)
                        .get("users");

        Object id = response.path("data.id[0]");
        System.out.println("id = " + id);
        Assert.assertEquals(1, id);
        response.getBody().prettyPrint();
    }

    @Test
    public void getFirstEmployeeName() {

    }

    @Test
    public void tryQueryParameters() {

    }

    @Test
    public void tryPathParameters() {
        Response response =
                given().baseUri(secondUrl).basePath("/api/").contentType(ContentType.JSON)
                        .pathParam("id", 2)
                        .get("users/{id}");
        response.getBody().prettyPrint();

    }

    @Test
    public void tryVerifyEmployee() {

    }

    @Test
    public void postCreateEmployee() {
        String bodyRequest = "{\"name\":\"test\",\"salary\":\"123\",\"age\":\"23\"}";
        Response response =
                given().baseUri(firstUrl).basePath("/api/v1").contentType(ContentType.JSON)
                        .body(bodyRequest)
                        .post("/create");

        response.getBody().prettyPrint();
        Assert.assertEquals(response.path("status"), "success");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.path("data.name"), "test");
        int idEmployee = response.path("data.id");
        System.out.println("idEmployee = " + idEmployee);

    }

    @Test
    public void postCreateUser() {
        String bodyRequest = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";

        Response response =
                given().baseUri(secondUrl).basePath("/api").contentType(ContentType.JSON)
                        .body(bodyRequest)
                        .post("/users");

        response.getBody().prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.path("name"), "morpheus");

    }

    @Test
    public void updateUser() {
        String bodyRequest = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";

        Response response =
                given().baseUri(secondUrl).basePath("/api").contentType(ContentType.JSON)
                        .body(bodyRequest)
                        .post("/users");

        String idUser = response.path("id");

        String updateBodyRequest = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"CEO\"\n" +
                "}";

        Response updateRespond =
                given().baseUri(secondUrl).basePath("/api").contentType(ContentType.JSON)
                        .body(updateBodyRequest)
                        .pathParam("id", idUser)
                        .post("/users/{id}");

        String patchBodyRequest = "{\n" +
                "    \"job\": \"Anak Adam\"\n" +
                "}";

        Response patchRespond =
                given().baseUri(secondUrl).basePath("/api").contentType(ContentType.JSON)
                        .body(updateBodyRequest)
                        .pathParam("id", idUser)
                        .patch("/users/{id}");

        response.getBody().prettyPrint();
        updateRespond.getBody().prettyPrint();
        patchRespond.getBody().prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.path("name"), "morpheus");
    }

    @Test
    public void deleteUser() {

        String bodyRequest = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";

        Response response =
                given().baseUri(secondUrl).basePath("/api").contentType(ContentType.JSON)
                        .body(bodyRequest)
                        .post("/users");

        String idUser = response.path("id");

        Response deleteResponse =
                given().baseUri(secondUrl).basePath("/api").contentType(ContentType.JSON)
                        .pathParam("id", idUser)
                        .delete("/users/{id}");
        Assert.assertEquals(204, deleteResponse.getStatusCode());

    }
}
