package com.zll.xunyiwenyao.test;

import java.util.Map;

import org.json.JSONException;

import com.zll.xunyiwenyao.util.JsonHelper;

public class MyTest {

	public static void main(String[] args) {
		try {
            //String url = "http://222.29.100.155/b2b2c/api/mobile/doctor/getAllDoctor.do";
            String resultJson = "{\"result\":1,\"message\":null,\"data\":[{\"fields\":{},\"doctor_id\":2,\"reg_name\":\"10002\",\"real_name\":\"苏轼\",\"sex\":0,\"sex_text\":\"女\",\"type\":0,\"type_text\":\"执业医师\",\"photo\":null,\"license\":null,\"status\":0,\"status_text\":\"离线\",\"title\":\"副主任医师\",\"hospital\":\"北京协和医院\",\"department\":\"外科\",\"goodat\":\"苏轼是我国北宋时期著名的大文学家。他不但对诗文、书法造诣很深，而且堪称我国古代美食家，对烹调菜肴亦很有研究，尤其擅长制作红烧肉追本穷源，苏轼的这种红烧肉最早在徐州的创制，在黄州时得到进一步提高，在杭州时闻名全国。关于东坡肉名字的由来有很多传说，其中一种传说：\\n　　相传苏东坡在徐州、黄州、杭州三个地方做过“东坡肉”。在任徐州知州时带领百姓抗洪筑堤保城，百姓纷纷杀猪宰羊上府慰劳，东坡推辞不掉，收下后亲自指点家人烧制红烧肉回赠予老百姓。大家食后，都觉得此肉肥而不腻、酥香味美，一致称它为“回赠肉”。\\n　　元丰三年（1080）二月一日，苏轼被谪贬到黄州，见黄州市面猪肉价贱，而人们不大吃它，便亲自烹调猪肉。有一次他食得兴起，即兴作了一首打油诗名曰《食猪肉诗》，诗中写道：“黄州好猪肉，价贱如粪土。富者不肯吃，贫者不解煮。慢着火，少着水，火候足时它自美。每日早来打一碗，饱得自家君莫管。”此诗一传十，十传百，人们开始争相仿制，并把这道菜戏称为“东坡肉”。　苏东坡二任杭州知州时，组织民工疏浚西湖，筑堤建桥，使葑草湮没大半的西湖重新恢复昔日美景，杭州的老百姓非常感谢他，过年时，大家就抬猪担酒来给他拜年。苏东坡收到后，便指点家人将肉切成方块，烧得红酥醇香分送给参加疏浚西湖的民工们吃，大家吃后无不赞赏称奇，于是“东坡肉”的美名更传遍了全国。\",\"profile\":null,\"grade\":0,\"sum_up\":0.0,\"register_time\":0,\"register_time_text\":\"\",\"password\":null},{\"fields\":{},\"doctor_id\":3,\"reg_name\":\"10003\",\"real_name\":\"王维\",\"sex\":0,\"sex_text\":\"女\",\"type\":1,\"type_text\":\"审核医师\",\"photo\":null,\"license\":null,\"status\":1,\"status_text\":\"在线\",\"title\":\"住院医师\",\"hospital\":\"北京协和医院\",\"department\":\"内科\",\"goodat\":\"王维的大多数诗都是山水田园之作，在描绘自然美景的同时，流露出闲居生活中闲逸萧散的情趣。王维的写景诗篇，常用五律和五绝的形式，篇幅短小，语言精美，音节较为舒缓，用以表现幽静的山水和诗人恬适的心情，尤为相宜。王维从中年以后日益消沉，在佛理和山水中寻求寄托，他自称“一 悟寂为乐，此生闲有余”（《饭覆釜山僧》）。\",\"profile\":null,\"grade\":0,\"sum_up\":0.0,\"register_time\":0,\"register_time_text\":\"\",\"password\":null},{\"fields\":{},\"doctor_id\":4,\"reg_name\":\"10004\",\"real_name\":\"李商隐\",\"sex\":0,\"sex_text\":\"女\",\"type\":1,\"type_text\":\"审核医师\",\"photo\":null,\"license\":null,\"status\":1,\"status_text\":\"在线\",\"title\":\"住院医师\",\"hospital\":\"北京中医院\",\"department\":\"放射科\",\"goodat\":\"李商隐通常被视作唐代后期最杰出的诗人，其诗风受李贺影响颇深，在句法、章法和结构方面则受到杜甫和韩愈的影响。许多评论家认为，在唐朝的优秀诗人中，他的重要性仅次于杜甫、李白、王维等人。就诗歌风格的独特性而言，他与其他任何诗人相比都不逊色。但用典相对较多，有晦涩之嫌。\",\"profile\":null,\"grade\":0,\"sum_up\":0.0,\"register_time\":0,\"register_time_text\":\"\",\"password\":null},{\"fields\":{},\"doctor_id\":30,\"reg_name\":\"10001\",\"real_name\":\"李清照\",\"sex\":1,\"sex_text\":\"男\",\"type\":1,\"type_text\":\"审核医师\",\"photo\":null,\"license\":null,\"status\":1,\"status_text\":\"在线\",\"title\":\"副主任医师\",\"hospital\":\"北京中医院\",\"department\":\"内科\",\"goodat\":\"李清照工诗善文，更擅长词。李清照词，人称“易安词”、“漱玉词”，以其号与集而得名。《易安集》、《漱玉集》，宋人早有著录。其词流传至今的，据今人所辑约有45首，另存疑10余首。她的《漱玉词》既男性亦为之惊叹。她不但有高深的文学修养，而且有大胆的创造精神。从总的情况看，她的创作内容因她在北宋和南宋时期生活的变化而呈现出前后期不同的特点。\",\"profile\":null,\"grade\":0,\"sum_up\":0.0,\"register_time\":0,\"register_time_text\":\"\",\"password\":null},{\"fields\":{},\"doctor_id\":32,\"reg_name\":\"111\",\"real_name\":\"礼俗\",\"sex\":0,\"sex_text\":\"女\",\"type\":1,\"type_text\":\"审核医师\",\"photo\":null,\"license\":null,\"status\":0,\"status_text\":\"离线\",\"title\":\"住院医师\",\"hospital\":\"湖南省人民医院\",\"department\":\"内科\",\"goodat\":\"好吧汇报了\\n\",\"profile\":\"\",\"grade\":0,\"sum_up\":0.0,\"register_time\":1493361246,\"register_time_text\":\"2017-04-28 14:34\",\"password\":null}]}";
            //String s = HttpHelper.sendGet(url, "");
            System.out.println("receive:"+resultJson);
            Map m = JsonHelper.toMap(resultJson);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
}
