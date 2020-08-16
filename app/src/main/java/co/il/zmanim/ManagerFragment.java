package co.il.zmanim;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import co.il.zmanim.room.MessageDatabase;


public class ManagerFragment extends Fragment {


    public static final String TAG = ManagerFragment.class.getSimpleName();
    private ManagerFragmentListener mListener;
    private RecyclerView mRecyclerView;
    private MessageAdapter mMessageAdpater;
    private View mAddMessageBtn;

    public ManagerFragment() {
        // Required empty public constructor
    }


    public static ManagerFragment newInstance() {
        ManagerFragment fragment = new ManagerFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manager, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
        initListeners();
        setMessageRecyclerView();

//        getView().findViewById(R.id.MAA_b).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                MainViewModel model;
//
//                model = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
//                model.getFruitList().setValue("87576575");
//
//            }
//        });
    }



    private void initViews() {


        mAddMessageBtn = getView().findViewById(R.id.FM_add_message_Btn);


    }



    private void initListeners() {


        mAddMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.onAddMessageClicked();

            }
        });


    }





    private void setMessageRecyclerView() {

        final MessageDatabase messageDatabase = MessageDatabase.getInstance(getContext());

        mRecyclerView = getView().findViewById(R.id.FM_recycler_RV);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final List<MessageObject> messageObjectList = new ArrayList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {

                messageObjectList.addAll(messageDatabase.messageDao().getMessagesList());

                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mMessageAdpater = new MessageAdapter(getContext(), messageObjectList);
                        mRecyclerView.setAdapter(mMessageAdpater);
                    }
                });
            }
        }).start();



    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ManagerFragmentListener) {
            mListener = (ManagerFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ManagerFragmentListener");
        }
    }


    interface ManagerFragmentListener {


        void onAddMessageClicked();
    }


    public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {


        private final Context mContext;
        private final List<MessageObject> mMessageList;

        MessageAdapter(Context context, List<MessageObject> messagesList) {

                mContext = context;
                mMessageList = messagesList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

            holder.updateItem(mMessageList.get(position));

            holder.mRemoveMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            MessageDatabase messageDatabase = MessageDatabase.getInstance(getContext());
                            messageDatabase.messageDao().deleteMessage(mMessageList.get(position));
                            Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    mMessageList.remove(position);
                                    notifyDataSetChanged();
                                }
                            });

                        }
                    }).start();



                }
            });
        }

        @Override
        public int getItemCount() {
            return mMessageList.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView mMessage;
            private final Button mRemoveMessage;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                mMessage = itemView.findViewById(R.id.MI_message_TV);
                mRemoveMessage = itemView.findViewById(R.id.MI_remove_Btn);
            }


            public void updateItem(MessageObject messageObject) {


                mMessage.setText(messageObject.getMessage());





            }
        }
    }


}
