package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.TabFavorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        Favorite favorite = null;
        try {
            String sql = " select * from tab_favorite where rid = ? and uid = ?";
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return favorite;
    }

    @Override
    public int findCountByRid(int rid) {
        String sql = "SELECT COUNT(*) FROM tab_favorite WHERE rid = ?";

        return template.queryForObject(sql,Integer.class,rid);
    }

    @Override
    public void add(int rid, int uid) {
        String sql = "insert into tab_favorite values(?,?,?)";

        template.update(sql,rid,new Date(),uid);
    }

    public int findTotalCountByUid(int uid) {
        String sql = "select count(*) from tab_favorite where uid = ?";
        return template.queryForObject(sql,Integer.class,uid);
    }
    //分页查询出用户收藏TabFavorite信息
    public List<TabFavorite> findByUid(int uid, int start, int pageSize) {
        String sql = "select * from tab_favorite where uid = ? limit ?, ?";
        return template.query(sql,new BeanPropertyRowMapper<TabFavorite>(TabFavorite.class),uid,start,pageSize);
    }

    //查询收藏次数
    public int findByRid(int rid) {
        int favoriteCount = -1;
        String sql = "select count(*) from tab_favorite where rid = ? ";
        try {
            favoriteCount = template.queryForObject(sql,Integer.class,rid);
        } catch (DataAccessException e) {
            System.out.println("FavoriteDao查询收藏次数失败");
        }
        return favoriteCount;
    }
}
