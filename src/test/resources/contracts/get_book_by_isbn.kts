import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

listOf(
        contract {
            description = "should return book by isbn 1234567890"

            request {
                url = url("/books/1234567890")
                method = GET
            }

            response {
                status = OK
                headers {
                    header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                }

                body = body(mapOf(
                        "id" to 1,
                        "isbn" to "1234567890"
                    )
                )
            }

        },
        contract {
            description = "should return 404 by isbn 0987654321"

            request {
                url = url("/books/0987654321")
                method = GET
            }

            response {
                status = NOT_FOUND
            }

        }
)
