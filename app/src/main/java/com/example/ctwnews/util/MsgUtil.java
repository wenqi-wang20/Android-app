package com.example.ctwnews.util;

import com.example.ctwnews.R;
import com.example.ctwnews.models.news.Msg;

import java.util.ArrayList;
import java.util.List;

public class MsgUtil {
    public static List<Msg> getMsgList(){
        List<Msg> msgList = new ArrayList<>();
        Msg msg = new Msg(1, R.drawable.img_test,
                "华为新一代芯片震撼众人，麒麟990颠覆你认知！",
                "就在近日，vivo旗下的一款子品牌IQOO在深圳首发了它的第一款新机，该消息一经发布，就引得众人纷纷赶来为了一睹其风采，在当时也引起了各界媒体的关注与争先报道。");
        msgList.add(msg);
        msg = new Msg(2,R.drawable.img_test,
                "网络提速降费今年将放出四个大招!",
                "今年两会政府工作报告中提出，今年中小企业宽带平均资费再降低15%，移动网络流量平均资费再降低20%以上。随后中国移动、中国电信、中国联通三大运营商作出回应，坚决落实提速降费。");
        msgList.add(msg);
        msg = new Msg(3,R.drawable.img_test,
                "【早报】传谷歌第一款智能手表3月或6月上市",
                "外媒称，传闻已久的谷歌智能手表确实已经存在，而且谷歌公司预计它将于今年3月中旬至下旬上市。");
        msgList.add(msg);
        msg = new Msg(4,R.drawable.img_test,
                "外媒详测：iPhone XS信号真的有问题吗",
                "高通在基带芯片上的垄断地位即使是苹果也无法撼动，该交的专利费依旧要交。苹果在今年发布的三款iPhone上，全部采用英特尔的调制解调器和通信芯片，并且苹果今年采用了四天线方案.");
        msgList.add(msg);
        msg = new Msg(5,R.drawable.img_test,
                "不仅要卖芯片，英特尔还想做 5G 时代的云端生意",
                "上网速度更快，是大部分消费者对于 5G 网络最直观的印象，但和 4G 相比增速是否明显，高速网络又有什么新玩法，在 5G 正式落地前，我们都不好妄加揣测。");
        msgList.add(msg);
        return msgList;
    }
}