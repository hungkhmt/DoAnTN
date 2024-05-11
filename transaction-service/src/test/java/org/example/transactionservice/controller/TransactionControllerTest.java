package org.example.transactionservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.transactionservice.dto.transaction.TransferDto;
import org.example.transactionservice.dto.transaction.WithdrawDto;

import org.junit.jupiter.api.RepeatedTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(TransactionController.class) //use to test call api
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionController transactionController;


    @RepeatedTest(1000)
    public void testTransfer_Success() throws Exception {
        // Arrange
        TransferDto transferDto = new TransferDto(3L, 5L, 100.0, "test");


        // Act
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder
                = MockMvcRequestBuilders
                .post("/api/v1/tracsaction/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(transferDto));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk());
    }

    @RepeatedTest(1000)
    public void testWithdraw() throws Exception {
        // Arrange
        WithdrawDto withdrawDto = WithdrawDto.builder()
                .accountId(1L)
                .amount(50D)
                .description("Test rut").build();

        // Act
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder
                = MockMvcRequestBuilders
                .post("/api/v1/tracsaction/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(withdrawDto));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk());


    }

}
