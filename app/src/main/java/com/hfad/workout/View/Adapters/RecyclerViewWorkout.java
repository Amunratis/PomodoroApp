package com.hfad.workout.View.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hfad.workout.Model.Workout;
import com.hfad.workout.R;
import com.hfad.workout.View.Adapters.helper.ItemTouchHelperAdapter;
import com.hfad.workout.View.Adapters.helper.ItemTouchHelperViewHolder;
import com.hfad.workout.View.Adapters.helper.OnStartDragListener;

import java.util.ArrayList;
import java.util.Collections;

public class RecyclerViewWorkout extends RecyclerView.Adapter<RecyclerViewWorkout.ViewHolder>
        implements ItemTouchHelperAdapter
    {


Drawable drawable;

    private ArrayList<Workout> listWorkout;

        private final OnStartDragListener mDragStartListener;

    private Context mContext;//TODO simplify it

        @Override
        public boolean onItemMove(int fromPosition, int toPosition) {
            Collections.swap(listWorkout, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onItemDismiss(int position) {
            listWorkout.remove(position);
            notifyItemRemoved(position);

        }

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    final private ListItemClickListener mOnClickListener;

    public RecyclerViewWorkout(ArrayList<Workout> listWorkout, Context mContext, ListItemClickListener listener,OnStartDragListener dragStartListener){
        this.listWorkout = listWorkout;
        this.mContext = mContext;
        mOnClickListener=listener;
        mDragStartListener = dragStartListener;
    }


    @Override
    public RecyclerViewWorkout.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workout_item, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;

        TextView name = (TextView)cardView.findViewById(R.id._name);
        name.setText(listWorkout.get(position).getName());

        TextView description = (TextView)cardView.findViewById(R.id._description);
        description.setText(listWorkout.get(position).getDescription());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ItemTouchHelperViewHolder {

        private CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView =cardView;
            cardView.setOnClickListener(this);
        }
        @Override
        public void onClick (View view){
            int clickedPosition=getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
            drawable=itemView.getBackground();
        }

        @Override
        public void onItemClear() {
            itemView.setBackground(drawable);
            itemView.setBackgroundColor(Color.WHITE);
            itemView.setBackground(drawable);//TODO 12/10/2017 tried to set CardView's
            //back again, work on saving changes to database and to adapter as well
        }
    }

    @Override
    public int getItemCount() {
        return listWorkout.size();
    }
    //TODO how does this adapter differ from drawer adapter
}
