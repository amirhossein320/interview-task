package ir.sanags.interviewTask.presenter.addresses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ir.sanags.interviewTask.data.api.address.AddressResponse
import ir.sanags.interviewTask.databinding.ItemAddressBinding

class AddressesAdapter :
    ListAdapter<AddressResponse, AddressesAdapter.AddressesViewHolder>(AddressesDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressesViewHolder {
        val binding =
            ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddressesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AddressesViewHolder(private val binding: ItemAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AddressResponse) {
            with(binding) {
                binding.txtAddress.text = item.address
                binding.txtName.text = "${item.firstName} ${item.lastName}"
                binding.txtMobile.text = item.coordinateMobile.toString()
            }
        }
    }
}


private class AddressesDiffUtil : DiffUtil.ItemCallback<AddressResponse>() {
    override fun areItemsTheSame(oldItem: AddressResponse, newItem: AddressResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AddressResponse, newItem: AddressResponse): Boolean {
        return oldItem == newItem
    }

}