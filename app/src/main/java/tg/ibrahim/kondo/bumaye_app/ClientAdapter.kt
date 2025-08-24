package tg.ibrahim.kondo.bumaye_app

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class ClientAdapter (list : ArrayList<Client>) : RecyclerView.Adapter<ClientAdapter.ViewHolder>()  {
    var clientList = list

    interface OnItemClickListener {
        fun onItemClick(client: Client)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(clickListener: OnItemClickListener) {
        listener = clickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Client>) {
        clientList = ArrayList(newList)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_client, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        var client = clientList[position]
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        holder.fullNameTv.text = "${client.lastName} ${client.firstName}"
        holder.orderDateTv.text = "Date commande : " + client.orderDate
        holder.deliveryDateTv.text = "Date livraison : " + client.deliveryDate
        holder.amountTv.text = "Total : " + client.totalAmount.toString()
        holder.amountAdvanceTv.text = "Payé : " + client.advance.toString()
        holder.phoneTv.text = client.phoneNumber
        holder.itemView.setOnClickListener {
            listener?.onItemClick(client)
        }

    }

    override fun getItemCount(): Int {
        return clientList.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var fullNameTv = view.findViewById<TextView>(R.id.item_full_name_tv)
        var phoneTv = view.findViewById<TextView>(R.id.item_phone_tv)
        var orderDateTv = view.findViewById<TextView>(R.id.item_order_date_tv)
        var deliveryDateTv = view.findViewById<TextView>(R.id.item_delivery_date_tv)
        var amountTv = view.findViewById<TextView>(R.id.item_amount_tv)
        var amountAdvanceTv = view.findViewById<TextView>(R.id.item_amount_advance_tv)
    }
}