import io.restassured.http.ContentType;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class TesteCliente {

    String enderecoApiCliente = "http://localhost:8080/";
    String endpointCliente = "cliente";
    String endpointDeletarCliente = "cliente/104";

    @Test
    @DisplayName("Quando pegar todos os clientes sem cadastrar, então a lista deve estar vazia")
    public void pegaTodosClientes() {

        String respostaEsperada = "{}";

        given()
                .contentType(ContentType.JSON)

                .when()
                .get(enderecoApiCliente)

                .then()
                .statusCode(200)
                .assertThat().body(new IsEqual<>(respostaEsperada));

    }

    @Test
    @DisplayName("Quando cadastrar novo cliente, então ele deve estar disponível na lista")
    public void CadastraNovoCliente() {

        String clienteParaCadastrar = "{\n" +
                "\"id\": 104,\n" +
                "  \"idade\": 22,\n" +
                "  \"nome\": \"BIA\",\n" +
                "  \"risco\": 0\n" +
                "}";

        String respostaEsperada = "{\"104\":{\"nome\":\"BIA\",\"idade\":22,\"id\":104,\"risco\":0}}";

        given()
                .contentType(ContentType.JSON)
                .body(clienteParaCadastrar)
                .when()
                .post(enderecoApiCliente + endpointCliente)
                .then()
                .statusCode(201)
                .assertThat().body(containsString(respostaEsperada));
    }

        @Test
        @DisplayName("Quando atualizar cliente já cadastrado, então ele deve serr atualizado")
        public void AtualizarClienteCadastrado() {

            String clienteParaCadastrar = "{\n" +
                    "\"id\": 104,\n" +
                    "  \"idade\": 22,\n" +
                    "  \"nome\": \"MANU\",\n" +
                    "  \"risco\": 0\n" +
                    "}";

            String clienteParaAtualizar = "{\n" +
                    "\"id\": 104,\n" +
                    "  \"idade\": 10,\n" +
                    "  \"nome\": \"MANU\",\n" +
                    "  \"risco\": 0\n" +
                    "}";

    String AtualizacaoEsperada = "{\"104\":{\"nome\":\"MANU\",\"idade\":10,\"id\":104,\"risco\":0}}";

            given()
                    .contentType(ContentType.JSON)
                    .body(clienteParaCadastrar)
                    .when()
                    .post(enderecoApiCliente + endpointCliente)
                    .then()
                    .statusCode(201);

            //ATUALIZAR

        given()
                .contentType(ContentType.JSON)
                .body(clienteParaAtualizar)

        .when()
                .put(enderecoApiCliente+endpointCliente)
        .then()
                .statusCode(200)
                .assertThat().body(containsString(AtualizacaoEsperada));
}

@Test
    @DisplayName("Quando deletar cliente cadastrado, então ele deve ser deletado")
    public void DeletarClienteCadastradoporID() {

    String clienteParaCadastrar = "{\n" +
            "\"id\": 104,\n" +
            "  \"idade\": 10,\n" +
            "  \"nome\": \"MANU\",\n" +
            "  \"risco\": 0\n" +
            "}";

    String respostaEsperada = "CLIENTE REMOVIDO: { NOME: MANU, IDADE: 10, ID: 104 }";


    given()
            .contentType(ContentType.JSON)
            .body(clienteParaCadastrar)
            .when()
            .post(enderecoApiCliente + endpointCliente)
            .then()
            .statusCode(201);

    //DELETAR

    given()
            .contentType(ContentType.JSON)

    .when()
           // delete(enderecoApiCliente+endpointDeletarCliente) //essa opção é opcional
           .delete(enderecoApiCliente+endpointCliente+"/104") //deu errado pq faltava o "/"

    .then()
            .statusCode(200)
            .assertThat().body(new IsEqual<>(respostaEsperada));
}


        }

