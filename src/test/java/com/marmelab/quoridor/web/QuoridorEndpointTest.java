package com.marmelab.quoridor.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class QuoridorEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("get game should return the board with the pawn")
    public void gameWithBoardAndPawn() throws Exception {
        MvcResult quoridor = this.mockMvc.perform(get("/quoridor"))
                .andExpect(status().isOk())
                .andExpect(view().name("quoridor"))
                .andReturn();

        String content = quoridor.getResponse().getContentAsString();
        assertThat(content)
                .contains("<div class=\"pawn\" style=\"top: 160px; left: 0px\">")
                .contains("<div class=\"square\" style=\"top: 0px; left: 80px\"></div>");
    }

}