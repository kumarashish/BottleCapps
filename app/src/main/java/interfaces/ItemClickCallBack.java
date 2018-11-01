package interfaces;


import model.OrderModel;

/**
 * Created by Ashish.Kumar on 18-05-2018.
 */

public interface ItemClickCallBack  {
   public void OnItemClick(OrderModel model,int i);
   public void OnStatusClicked(OrderModel model,int i);
}
