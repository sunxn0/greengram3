package com.green.greengram3.feed;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram3.common.ResVo;
import com.green.greengram3.feed.model.FeedDelDto;
import com.green.greengram3.feed.model.FeedInsDto;
import com.green.greengram3.feed.model.FeedSelProcVo;
import com.green.greengram3.feed.model.FeedSelVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FeedController.class)//빈등록된 컨트롤러를 객체화시킴
class FeedControllerTest {

    @Autowired
    private MockMvc mvc; //delete할지 post할지 쏴주는역할..?
    @MockBean//아무것도 값을 넣지않고 리턴하면 리턴해주는 타입의 디폴드값을 리턴함
    private FeedService service;//실제 컨트롤러에서 service를 사용하고 있어서
    @Autowired
    private ObjectMapper mapper;

    @Test
    void postFeed() throws Exception {
        ResVo result = new ResVo(2);
        //when(service.postFeed(any())).thenReturn(result);
        given(service.postFeed(any())).willReturn(result);// when이랑 같은 역할 뭐가들어오든 result를 리턴해라

        FeedInsDto dto = new FeedInsDto();

        mvc.perform( //포스트맨에서 send보내는거랑 비슷함 perform메소드 안에 파라미터 type은 MockMvcrequestBuilder
                        MockMvcRequestBuilders
                                .post("/api/feed")
                                .contentType(MediaType.APPLICATION_JSON) //MediaType을  JSON으로 setting Body부분에 있음
                                .content(mapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(result)))//응답할때 바디에 담겨진게 기대한 문자열과같은지
                //{ "result" :5}인 형태로 넘어올거임 <-given(service.postFeed(any())).willReturn(result); 둘을 보고 알 수 있는것
                .andDo(print());
//        System.out.println("result :" +mapper.writeValueAsString(result));

        verify(service).postFeed(any());

    }

    @Test
    void selFeed() throws Exception {


//        MultiValueMap<String, String> params = new LinkedMultiValueMap();
//        params.add("page", "1");
//        params.add("loginedIuser","2"); //param으로 보내주기 위해


//
//        mvc.perform(
//                MockMvcRequestBuilders
//                        .get("/api/feed")
//                        .params(params)
//        ).andDo(print());//통신결과
//
//        verify(service).selFeed(any());

        List<FeedSelVo> list = new ArrayList();

        FeedSelVo item = new FeedSelVo(); //given when then 으로 test
        item.setContents("zz");
        item.setIfeed(1);
        item.setWriterNm("heasun");

        list.add(item);

        given(service.selFeed(any())).willReturn(list);

        String json = mapper.writeValueAsString(list);
        System.out.println("test-json: " + json);

        mvc.perform( //검증하는 부분
                        MockMvcRequestBuilders
                                .get("/api/feed")
                )
                //기대하는부분
                .andExpect(status().isOk())
                .andExpect(content().string(json))
                .andDo(print());

        verify(service).selFeed(any());


    }

    @Test
    void toggleFeedFav() {
    }

    @Test
    void delFeed()throws Exception{
        ResVo result = new ResVo(1);

        given(service.delFeed(any())).willReturn(result);


        String json = mapper.writeValueAsString(result);

        mvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/feed")
        )
                .andExpect(status().isOk())
                .andExpect(content().string(json))
                .andDo(print());
        verify(service).delFeed(any());
//        FeedDelDto info = new FeedDelDto();
//        info.setIuser(1);
//        info.setIfeed(1);

    }
}