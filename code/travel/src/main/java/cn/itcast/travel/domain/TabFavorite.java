package cn.itcast.travel.domain;

/**
 * @author XUAN
 * @date 2020/10/22 - 0:21
 * @references
 * @purpose
 * @errors
 */
public class TabFavorite {
    private int rid;//旅游线路对象
    private String date;//收藏时间
    private int uid;//所属用户

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
