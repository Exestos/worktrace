package com.exestos.worktrace.utils;

import com.exestos.worktrace.domain.Group;
import com.exestos.worktrace.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Component
public class MapUtils {
    private final ObjectMapper mapper;

    public MapUtils() {
        mapper = new ObjectMapper();
    }

    public <T> ArrayNode mapCollection(Collection<T> collection, Function<T, ObjectNode> typeMapper) {
        List<ObjectNode> mappedCollection = collection.stream().map(typeMapper).toList();
        ArrayNode arrayNode = mapper.createArrayNode();
        arrayNode.addAll(mappedCollection);
        return arrayNode;
    }

    public ObjectNode mapGroupToDTO(Group group) {
        ObjectNode groupDto = mapper.createObjectNode();
        groupDto.put("id", group.getId());
        groupDto.put("title", group.getTitle());
        if (group.getCreator() != null) {
            groupDto.set("creator", mapUserToDTO(group.getCreator()));
        }
        if (group.getUsers() != null) {
            groupDto.set("users", mapCollection(group.getUsers(), this::mapUserToDTO));
        }
        return groupDto;
    }

    public ObjectNode mapUserToDTO(User user) {
        ObjectNode userDto = mapper.createObjectNode();
        userDto.put("id", user.getId());
        userDto.put("username", user.getUsername());
        userDto.put("first_name", user.getFirstName());
        userDto.put("last_name", user.getLastName());
        userDto.put("is_admin", user.isAdmin());
        if (user.getGroups() != null) {
            userDto.set("groups", mapCollection(user.getGroups(), this::mapGroupToDTO));
        }
        return userDto;
    }
}
