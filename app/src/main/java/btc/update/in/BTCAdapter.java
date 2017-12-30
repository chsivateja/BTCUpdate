package btc.update.in;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class BTCAdapter extends RecyclerView.Adapter<BTCAdapter.MyViewHolder> {

    private Context mContext;
    private List<BTC> btcList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView buy, sell;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            buy = (TextView) view.findViewById(R.id.buy);
            sell = (TextView) view.findViewById(R.id.sell);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

        }
    }


    public BTCAdapter(Context mContext, List<BTC> btcList) {
        this.mContext = mContext;
        this.btcList = btcList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rate_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final BTC btc = btcList.get(position);
        holder.buy.setText(btc.getBuy());
        holder.sell.setText(btc.getSell());

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(btc.getRef()); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                v.getContext().startActivity(intent);
            }
        });

        // loading btc cover using Glide library
        Glide.with(mContext).load(btc.getThumbnail()).into(holder.thumbnail);

//        holder.overflow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPopupMenu(holder.overflow);
//            }
//        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    /*private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        //inflater.inflate(R.menu.menu_btc, popup.getMenu());
        //popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        //popup.show();
    }*/

    /**
     * Click listener for popup menu items
     */
//    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
//
//        public MyMenuItemClickListener() {
//        }
//
//        @Override
//        public boolean onMenuItemClick(MenuItem menuItem) {
//            switch (menuItem.getItemId()) {
//                case R.id.action_add_favourite:
//                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.action_play_next:
//                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
//                    return true;
//                default:
//            }
//            return false;
//        }
//    }

    @Override
    public int getItemCount() {
        return btcList.size();
    }
}