package com.example.weizhenbin.mydemo.baidumap;

/**
 * @author zhenbinwei   (作者)
 * @version V1.0
 * @Title: BaiduMapMarkerManage
 * @ProjectName Show
 * @Package com.weizhenbin.show.baidumap
 * @date 2016/2/26 14:04
 * 用来管理地图上的覆盖物 以及时释放
 */
public class BaiduMapMarkerManage {

  /*  private static BaiduMapMarkerManage instance;
    //用来存放覆盖物 基于不同的界面 已类名键值做索引
    private HashMap<String,ArrayList<OverlayOptions>> overlayOptionses;
    private BaiduMapMarkerManage(){
        overlayOptionses=new HashMap<>();
    }

    public static BaiduMapMarkerManage getInstance(){
        if(instance==null){
            instance=new BaiduMapMarkerManage();
        }
        return instance;
    }

    //添加覆盖物 并添加到集合中
    public void addMarker(BaiduMap baiduMap,OverlayOptions options,String key){
        if(baiduMap==null||options==null){
            return;
        }
        baiduMap.addOverlay(options);
        if(overlayOptionses.containsKey(key)){
            overlayOptionses.get(key).add(options);
        }else{
            ArrayList<OverlayOptions> optionses=new ArrayList<>();
            optionses.add(options);
            overlayOptionses.put(key,optionses);
        }
    }

    //添加批量覆盖物 并添加到集合中
    public void addMarker(BaiduMap baiduMap,ArrayList<OverlayOptions> optionses,String key){
        if(baiduMap==null||optionses==null){
            return;
        }
        baiduMap.addOverlays(optionses);
        if(overlayOptionses.containsKey(key)){
            overlayOptionses.get(key).addAll(optionses);
        }else{
            overlayOptionses.put(key,optionses);
        }
    }

    public void releaseMarker(BaiduMap baiduMap,String key){
        if(baiduMap==null){
            return;
        }
        if(overlayOptionses.containsKey(key)){
           ArrayList<OverlayOptions> optionses=overlayOptionses.get(key);
            int size=optionses.size();
            for (int i = 0; i < size; i++) {
                OverlayOptions options=optionses.remove(i);
                if(options instanceof MarkerOptions){
                    ((MarkerOptions) options).getIcon().recycle();
                }
            }
        }
    }

    public void releaseAllMarker(BaiduMap baiduMap){
        if(baiduMap==null){
            return;
        }
        ArrayList<String> strings=new ArrayList<>(overlayOptionses.keySet());
        int size=strings.size();
        for (int i = 0; i < size; i++) {
            releaseMarker(baiduMap,strings.remove(i));
        }
        overlayOptionses.clear();
    }*/
}
