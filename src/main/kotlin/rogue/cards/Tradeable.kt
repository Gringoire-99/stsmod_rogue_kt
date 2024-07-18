package rogue.cards

interface Tradeable {
    //    是否开启交易功能，为真才能进行交易
    var isEnableTrade: Boolean
    fun onTrade()
}