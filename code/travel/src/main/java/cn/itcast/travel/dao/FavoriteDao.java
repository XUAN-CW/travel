package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.TabFavorite;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

public interface FavoriteDao {

    /**
     * 根据rid和uid查询收藏信息
     * @param rid
     * @param uid
     * @return
     */
    public Favorite findByRidAndUid(int rid, int uid);

    /**
     * 根据rid 查询收藏次数
     * @param rid
     * @return
     */
    public int findCountByRid(int rid);

    /**
     * 添加收藏
     * @param i
     * @param uid
     */
    void add(int i, int uid);

    void cancel(int rid, int uid);

    public int findTotalCountByUid(int uid);
    //分页查询出用户收藏TabFavorite信息
    public List<TabFavorite> findByUid(int uid, int start, int pageSize);
    public int findByRid(int rid);
}
