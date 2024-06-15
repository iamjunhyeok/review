package com.iamjunhyeok.review;

import com.iamjunhyeok.review.constant.VocaType;
import com.iamjunhyeok.review.domain.Voca;
import com.iamjunhyeok.review.repository.VocaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Initializer implements CommandLineRunner {
    private final VocaRepository vocaRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Voca> adjectives = List.of(Voca.of(VocaType.ADJECTIVE, "용감한"),
                Voca.of(VocaType.ADJECTIVE, "차분한"),
                Voca.of(VocaType.ADJECTIVE, "명랑한"),
                Voca.of(VocaType.ADJECTIVE, "영리한"),
                Voca.of(VocaType.ADJECTIVE, "창의적인"),
                Voca.of(VocaType.ADJECTIVE, "호기심많은"),
                Voca.of(VocaType.ADJECTIVE, "대담한"),
                Voca.of(VocaType.ADJECTIVE, "열정적인"),
                Voca.of(VocaType.ADJECTIVE, "우아한"),
                Voca.of(VocaType.ADJECTIVE, "활기찬"),
                Voca.of(VocaType.ADJECTIVE, "화려한"),
                Voca.of(VocaType.ADJECTIVE, "겁없는"),
                Voca.of(VocaType.ADJECTIVE, "친절한"),
                Voca.of(VocaType.ADJECTIVE, "부드러운"),
                Voca.of(VocaType.ADJECTIVE, "행복한"),
                Voca.of(VocaType.ADJECTIVE, "정직한"),
                Voca.of(VocaType.ADJECTIVE, "겸손한"),
                Voca.of(VocaType.ADJECTIVE, "기쁜"),
                Voca.of(VocaType.ADJECTIVE, "충실한"),
                Voca.of(VocaType.ADJECTIVE, "신비로운"),
                Voca.of(VocaType.ADJECTIVE, "다정한"),
                Voca.of(VocaType.ADJECTIVE, "달콤한"),
                Voca.of(VocaType.ADJECTIVE, "따뜻한"),
                Voca.of(VocaType.ADJECTIVE, "멋진"),
                Voca.of(VocaType.ADJECTIVE, "상냥한"),
                Voca.of(VocaType.ADJECTIVE, "생기있는"),
                Voca.of(VocaType.ADJECTIVE, "순수한"),
                Voca.of(VocaType.ADJECTIVE, "신나는"),
                Voca.of(VocaType.ADJECTIVE, "아름다운"),
                Voca.of(VocaType.ADJECTIVE, "용맹한"),
                Voca.of(VocaType.ADJECTIVE, "알뜰한"),
                Voca.of(VocaType.ADJECTIVE, "위대한"),
                Voca.of(VocaType.ADJECTIVE, "유쾌한"),
                Voca.of(VocaType.ADJECTIVE, "자상한"),
                Voca.of(VocaType.ADJECTIVE, "오묘한"),
                Voca.of(VocaType.ADJECTIVE, "흥미로운"),
                Voca.of(VocaType.ADJECTIVE, "강렬한"),
                Voca.of(VocaType.ADJECTIVE, "고요한"),
                Voca.of(VocaType.ADJECTIVE, "날카로운"),
                Voca.of(VocaType.ADJECTIVE, "놀라운"),
                Voca.of(VocaType.ADJECTIVE, "발빠른"),
                Voca.of(VocaType.ADJECTIVE, "빛나는"),
                Voca.of(VocaType.ADJECTIVE, "소중한"),
                Voca.of(VocaType.ADJECTIVE, "슬기로운"),
                Voca.of(VocaType.ADJECTIVE, "씩씩한"),
                Voca.of(VocaType.ADJECTIVE, "유능한"),
                Voca.of(VocaType.ADJECTIVE, "자비로운"),
                Voca.of(VocaType.ADJECTIVE, "정겨운"),
                Voca.of(VocaType.ADJECTIVE, "독특한"),
                Voca.of(VocaType.ADJECTIVE, "뛰어난"));
        vocaRepository.saveAll(adjectives);

        List<Voca> nouns = List.of(Voca.of(VocaType.NOUN, "호랑이"),
                Voca.of(VocaType.NOUN, "코끼리"),
                Voca.of(VocaType.NOUN, "독수리"),
                Voca.of(VocaType.NOUN, "사자"),
                Voca.of(VocaType.NOUN, "고래"),
                Voca.of(VocaType.NOUN, "여우"),
                Voca.of(VocaType.NOUN, "곰"),
                Voca.of(VocaType.NOUN, "상어"),
                Voca.of(VocaType.NOUN, "늑대"),
                Voca.of(VocaType.NOUN, "펭귄"),
                Voca.of(VocaType.NOUN, "나비"),
                Voca.of(VocaType.NOUN, "원숭이"),
                Voca.of(VocaType.NOUN, "판다"),
                Voca.of(VocaType.NOUN, "기린"),
                Voca.of(VocaType.NOUN, "돌고래"),
                Voca.of(VocaType.NOUN, "하마"),
                Voca.of(VocaType.NOUN, "캥거루"),
                Voca.of(VocaType.NOUN, "고양이"),
                Voca.of(VocaType.NOUN, "강아지"),
                Voca.of(VocaType.NOUN, "토끼"),
                Voca.of(VocaType.NOUN, "하늘"),
                Voca.of(VocaType.NOUN, "바다"),
                Voca.of(VocaType.NOUN, "강물"),
                Voca.of(VocaType.NOUN, "폭포"),
                Voca.of(VocaType.NOUN, "무지개"),
                Voca.of(VocaType.NOUN, "태양"),
                Voca.of(VocaType.NOUN, "언덕"),
                Voca.of(VocaType.NOUN, "절벽"),
                Voca.of(VocaType.NOUN, "파도"),
                Voca.of(VocaType.NOUN, "마을"),
                Voca.of(VocaType.NOUN, "도시"),
                Voca.of(VocaType.NOUN, "거리"),
                Voca.of(VocaType.NOUN, "공원"),
                Voca.of(VocaType.NOUN, "광장"),
                Voca.of(VocaType.NOUN, "시장"),
                Voca.of(VocaType.NOUN, "도서관"),
                Voca.of(VocaType.NOUN, "극장"),
                Voca.of(VocaType.NOUN, "박물관"),
                Voca.of(VocaType.NOUN, "미술관"),
                Voca.of(VocaType.NOUN, "학교"),
                Voca.of(VocaType.NOUN, "연구소"),
                Voca.of(VocaType.NOUN, "공항"),
                Voca.of(VocaType.NOUN, "터미널"),
                Voca.of(VocaType.NOUN, "자동차"),
                Voca.of(VocaType.NOUN, "비행기"),
                Voca.of(VocaType.NOUN, "열차"),
                Voca.of(VocaType.NOUN, "트럭"),
                Voca.of(VocaType.NOUN, "버스"),
                Voca.of(VocaType.NOUN, "헬리콥터"),
                Voca.of(VocaType.NOUN, "우주선"));
        vocaRepository.saveAll(nouns);
    }
}
