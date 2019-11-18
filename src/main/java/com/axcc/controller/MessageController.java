package com.axcc.controller;

import com.axcc.model.Message;
import com.axcc.service.MessageService;
import com.axcc.utils.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MessageController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MessageService messageService;

    /**
     * 发布信息
     *
     */
    @RequestMapping(value="/insertMessageByBean",method = RequestMethod.POST)
    public Map<String,Object> insertMessageByBean(@RequestParam(value="title",required=true) String title,
                                                  @RequestParam(value="contents",required = true) String contents){
        System.out.println("AAAAAA");
        System.out.println("BBBBBB2");
        logger.info("insertMessage---------start");
        //返回值
        Map<String,Object> result;
        //保存数据
        Message bean = new Message();
        bean.setTitle(title);
        bean.setContents(contents);
        Date date = new Date();
        bean.setCreateTime(date);
        bean.setIsDelete(0);
        int value = messageService.insertMessageByBean(bean);
        result = BaseResult.checkResult(value);
        logger.info("insertMessage---------end");
        return result;
    }

    /**
    * 获取消息的列表，分页显示
    */
    @RequestMapping(value="/listPageMessageByBean",method= RequestMethod.POST)
    public Map<String,Object> listMessageByBean(@RequestParam(value="pageNum",required=true) int pageNum,
                                              @RequestParam(value="pageSize",required=true) int pageSize){
        logger.info("listPageMessaage-----start");
        //返回值
        Map<String,Object> result = new HashMap<String,Object>();
        Message bean = new Message();
        bean.setIsDelete(0);
        //总数量
        int count = messageService.countMessageByBean(bean);
        //分页显示
        List<Message> ListMsg = messageService.listPageMessageByBean(bean,pageNum,pageSize);
        result.put("code", BaseResult.SUCCESS_CODE);
        result.put("msg",BaseResult.SUCCESS_MSG);
        result.put("count",count);
        result.put("info",ListMsg);
        logger.info("listPageMessaage-----end");
        return result;
    }

    /**
     * 查看信息详情，按主键查询
     */
    @RequestMapping(value="/getMessageById",method=RequestMethod.POST)
    public Map<String,Object> getMessageById(@RequestParam(value = "id") int id){
        logger.info("getMessageById----------start");
        Map<String,Object> result = new HashMap<String,Object>();
        Message message = messageService.getMessageById(id);
        result.put("code",BaseResult.SUCCESS_CODE);
        result.put("msg",BaseResult.SUCCESS_MSG);
        result.put("info",message);
        logger.info("getMessageById----------end");
        return result;
    }

    /**
     * 修改信息
     *
     **/
    @RequestMapping(value="/updateMessageByBean",method = RequestMethod.POST)
    public Map<String,Object> updateMessageByBean(@RequestParam(value = "id") int id,
                                                  @RequestParam(value = "title") String title,
                                                  @RequestParam(value = "contents") String contents){
        logger.info("updateMessageByBean------------start");
        //返回值
        Map<String,Object> result = new HashMap<String,Object>();
        //保存修改内容
        Message bean = new Message();
        Date date = new Date();
        bean.setId(id);
        bean.setTitle(title);
        bean.setContents(contents);
        bean.setUpdateTime(date);
        int value = messageService.updateMessageByBean(bean);
        result = BaseResult.checkResult(value);
        logger.info("updateMessageByBean------------end");
        return result;
    }

    /**
     * 删除消息(逻辑删除)
     *
     * */
    @RequestMapping(value = "/deleteMessageById",method = RequestMethod.POST)
    public Map<String,Object> deleteMessageById(@RequestParam(value = "id") int id){
        logger.info("deleteMessageByLd-------start");
        //返回类型
        Map<String,Object> result = new HashMap<String,Object>();
        Date date = new Date();
        Message bean = new Message();
        bean.setId(id);
        bean.setIsDelete(1);
        bean.setUpdateTime(date);
        int value = messageService.updateMessageByBean(bean);
        result = BaseResult.checkResult(value);
        logger.info("deleteMessageByLd-------end");
        return result;
    }
}
