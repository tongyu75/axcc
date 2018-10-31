package com.axcc.service;

import com.axcc.model.Message;

import java.util.List;

public interface MessageService {

  Message getMessageById(int id);

  Message getMessageByBean(Message bean);

  List<Message> listMessageByBean(Message bean);

  List<Message> listPageMessageByBean(Message bean, int pageNum, int pageSize);

  int countMessageByBean(Message bean);

  int insertMessageByBean(Message bean);

  int updateMessageByBean(Message bean);

  int deleteById(Integer id);

}
