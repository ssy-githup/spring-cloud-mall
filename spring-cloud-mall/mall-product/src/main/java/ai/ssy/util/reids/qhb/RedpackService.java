package ai.ssy.util.reids.qhb;

import ai.ssy.util.reids.RedisTemplateHelper;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @ClassName RedpackService
 * @Description: TODO
 * @Author Administrator
 * @Date 2020/4/5
 * @Version V1.0
 **/
@Service
public class RedpackService {

//    @Autowired
//    private RedisTemplateHelper redisTemplateHelper;

    /**
     * 生成红包
     * @param orderId
     */
    public void genRedpack(String orderId,int redPackCount,int totalAmount){
        Object cache = RedisTemplateHelper.getCache("hb:pool:"+orderId);
        System.out.println(cache);
        if (cache==null){
            System.out.println("cache!=null");
            //根据业务规则生成红包
            //totalAmount;//总的红包金额20元 也就是2000分

            //根据红包金额和要分发的分数 进行拆分
            int[] redpacks = doPartitionRedpack(totalAmount,redPackCount);

            //创建一个用于存储 拆分红包金额的数组
            Object[] hbs = new Object[redpacks.length];
            //将生成的红包push到redis中
            for (int i = 0;i < redpacks.length; i++){
                JSONObject object = new JSONObject();
                object.put("hbId", i+1); //红包ID
                object.put("amount", redpacks[i]);   //红包金额,存的是分
                hbs[i] = object;
            }
            RedisTemplateHelper.leftPush("hb:pool:"+orderId,hbs);
        }
    }

    /**
     * 划分红包
     * @param totalAmount 红包总额 单位：分
     * @param redPackCount 红包数量
     * @return
     */
    private int[] doPartitionRedpack(int totalAmount,int redPackCount) {
        Random random = new Random();
        int randomMax= totalAmount - redPackCount;//每个人至少分1分钱，2000 - 6 = 1994元 也就是要随机分的钱。
        //要把1994 随机分成6份，我们需要向1994 这个数字中插入5个点
        // 比如 6 100  500  500  1600 这5个数字把1994分成了6份：6分 94分 400分 0分 1000分 394分
        int[] posArray = new int[redPackCount-1];
        for (int i = 0;i < posArray.length; i++){
            int pos =  random.nextInt(randomMax);
            posArray[i] = pos;
        }
        Arrays.sort(posArray);//对数组进行排序
        //生成红包
        int[] redpacks = new int[redPackCount];
        for (int i = 0;i <= posArray.length; i++){
            if (i == 0){
                redpacks[i] = posArray[i] + 1;//第一份
            }else if(i == posArray.length){//如果循环到posArray.length，此时数组已越界1位，randomMax - 该值 + 1分钱=最后一份
                redpacks[i] = randomMax - posArray[i-1] + 1;
            }else {
                redpacks[i] = posArray[i] - posArray[i-1] + 1;
            }
        }
        return redpacks;
    }


    /**
     * 抢红包
     * @param userId
     * @param orderId
     */
    public String snatchRedpack(String userId,String orderId){


//        Object object = jedisUtils.eval(LuaScript.getHbLua,4,
//                RedisKeys.getHbPoolKey(orderId),//
//                RedisKeys.getDetailListKey(orderId),//
//                RedisKeys.getHbRdKey(orderId),String.valueOf(userId));
//
//        return (String) object;
//


        // 加载脚本文件
        ScriptSource scriptSource = new ResourceScriptSource(new ClassPathResource("HbLua.lua"));
        DefaultRedisScript defaultRedisScript = new DefaultRedisScript();
        defaultRedisScript.setScriptSource(scriptSource);
        // 设置脚本返回类型
        defaultRedisScript.setResultType(String.class);


        List<Object> keyList = new ArrayList<>();

        keyList.add("hb:pool:" + orderId);
        keyList.add("hb:detailList:" + orderId);
        keyList.add("hb:rd:" + orderId);

        keyList.add(userId);

        Object object = RedisTemplateHelper.execute(defaultRedisScript,keyList,keyList);
        System.out.println("object=================="+object);
        return String.valueOf(object);
    }

}
