import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg_carwash.R
import com.example.tfg_carwash.localData.ReservationWithLavado

class ReservationAdapter(private val reservations: List<ReservationWithLavado>) :
    RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {

    inner class ReservationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val washType: TextView = view.findViewById(R.id.tvItemWashType)
        val date: TextView = view.findViewById(R.id.tvItemDate)
        val time: TextView = view.findViewById(R.id.tvItemTimeSlot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reservation, parent, false)
        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val reservationWithLavado = reservations[position]
        holder.washType.text = "Tipo de lavado: ${reservationWithLavado.lavado.name}"
        holder.date.text = "Fecha: ${reservationWithLavado.reservation.date}"
        holder.time.text = "Hora: ${reservationWithLavado.reservation.time}"
    }

    override fun getItemCount(): Int = reservations.size
}
