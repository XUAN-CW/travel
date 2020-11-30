package edu.guet.service;

import edu.guet.domain.*;
import edu.guet.domain.Favorite;
import edu.guet.domain.PageBean;
import edu.guet.domain.User;

public interface FavoriteService {

    /**
     * 判断是否收藏
     * @param rid
     * @param uid
     * @return
     */
    public boolean isFavorite(String rid, int uid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    void add(String rid, int uid);

    void cancel(String rid, int uid);

    public PageBean<Favorite> myFavorite(User user, int currentPage, int pageSize);
}
