package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.FavoriteService;

import java.util.ArrayList;
import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    private RouteDao routeDao = new RouteDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {

        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);

        return favorite != null;//如果对象有值，则为true，反之，则为false
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }


    public PageBean<Favorite> myFavorite(User user, int currentPage,int pageSize) {
        List<Favorite> myFavorite = new ArrayList<>();
        PageBean<Favorite> pageBean = new PageBean<>();
        RouteImgDao routeImgDao = new RouteImgDaoImpl();
        SellerDao sellerDao = new SellerDaoImpl();
        //查询总线路条数
        int totalCount = favoriteDao.findTotalCountByUid(user.getUid());
        //计算总页面数
        int totalPage = (totalCount % pageSize ==0)?(totalCount/pageSize):(totalCount/pageSize+1);

        int start = (currentPage-1)*pageSize;
        //根据用户的uid去查tab_favorite表，返回用户收藏的路线，但这里的路线内容是路线rid
        List<TabFavorite> list=favoriteDao.findByUid(user.getUid(),start,pageSize);
        Route route = null;
        for (TabFavorite tabTavorite : list) {
            //根据路线的rid去查tab_route得到真正的路线信息
            route = routeDao.findRouteByRid(tabTavorite.getRid());
            //根据route的id 查询图片集合信息
            List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());
            //将集合设置到route对象
            route.setRouteImgList(routeImgList);
            //根据route的sid（商家id）查询商家对象
            Seller seller = sellerDao.findById(route.getSid());
            route.setSeller(seller);

            //通过rid查询tab_favorite表中该路线的收藏次数
            route.setCount(favoriteDao.findByRid(tabTavorite.getRid()));
            Favorite favorite = new Favorite(route,tabTavorite.getDate(),user);
            myFavorite.add(favorite);
        }
        pageBean.setTotalCount(totalCount);
        pageBean.setTotalPage(totalPage);
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        pageBean.setList(myFavorite);
        return pageBean;
    }
}
