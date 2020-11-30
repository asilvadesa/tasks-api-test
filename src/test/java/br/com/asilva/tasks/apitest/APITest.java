package br.com.asilva.tasks.apitest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;

public class APITest {

    @BeforeClass
    public static void setUp(){
        baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void deveRetornarTarefas(){

        given()
        .when()
                .get("/todo")
        .then()
                .statusCode(200);
    }

    @Test
    public void deveAdicionarTarefaComSucesso(){
        given()
                .body("{\"task\":\"Teste via API\", \"dueDate\":\"2020-12-30\"}")
                .contentType(ContentType.JSON)
        .when()
            .post("/todo")
        .then()
            .statusCode(201)
        ;
    }

    @Test
    public void naoDeveAdicionarTarefaComSucesso(){
        given()
                .body("{\"task\":\"Teste via API\", \"dueDate\":\"2010-12-30\"}")
                .contentType(ContentType.JSON)
        .when()
                .post("/todo")
        .then()
                .log().all()
                .statusCode(400)
                .body("message", Matchers.is("Due date must not be in past"))
        ;
    }

}


