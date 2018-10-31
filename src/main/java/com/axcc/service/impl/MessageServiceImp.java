package com.axcc.service.impl;

import com.axcc.dao.MessageDao;
import com.axcc.model.Message;
import com.axcc.service.MessageService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "messageService")
public class MessageServiceImp implements MessageService {

  @Resource
  private MessageDao messageDao;

  @Override
  public Message getMessageById(int id){
    return messageDao.getMessageById(id);
  }

  @Override
  public Message getMessageByBean(Message bean){
    return messageDao.getMessageByBean(bean);
  }

  @Override
  public List<Message> listMessageByBean(Message bean){
    return messageDao.listMessageByBean(bean);
  }

  @Override
  public List<Message> listPageMessageByBean(Message bean, int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    return messageDao.listMessageByBean(bean);
  }

  @Override
  public int countMessageByBean(Message bean){
    return messageDao.countMessageByBean(bean);
  }

  @Override
  public int insertMessageByBean(Message bean){
    return messageDao.insertMessageByBean(bean);
  }

  @Override
  public int updateMessageByBean(Message bean){
    return messageDao.updateMessageByBean(bean);
  }

  @Override
  public int deleteById(Integer id){
    return messageDao.deleteById(id);
  }
}
