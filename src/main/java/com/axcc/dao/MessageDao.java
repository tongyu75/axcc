package com.axcc.dao;

import com.axcc.model.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageDao {

    Message getMessageById(Integer id);

    Message getMessageByBean(Message bean);

    List<Message> listMessageByBean(Message bean);

    int countMessageByBean(Message bean);

    int insertMessageByBean(Message bean);

    int updateMessageByBean(Message bean);

    int deleteById(Integer id);

}