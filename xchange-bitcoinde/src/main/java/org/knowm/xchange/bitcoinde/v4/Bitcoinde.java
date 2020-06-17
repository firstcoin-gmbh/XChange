package org.knowm.xchange.bitcoinde.v4;

import java.io.IOException;
import java.math.BigDecimal;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.bitcoinde.dto.BitcoindeException;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeIdResponse;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeResponse;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeAccountLedgerWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeAccountWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeCompactOrderbookWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeOrderbookWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeTradesWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyOpenOrdersWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyTradesWrapper;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("v4")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitcoinde {

  @DELETE
  @Path("{trading_pair}/orders/{order_id}")
  BitcoindeResponse deleteOrder(
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest,
      @PathParam("order_id") String orderId,
      @PathParam("trading_pair") String tradingPair)
      throws IOException;

  @POST
  @Path("{trading_pair}/orders")
  BitcoindeIdResponse createOrder(
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest,
      @FormParam("max_amount_currency_to_trade") BigDecimal maxAmount,
      @FormParam("price") BigDecimal price,
      @PathParam("trading_pair") String tradingPair,
      @FormParam("type") String type)
      throws IOException;

  @GET
  @Path("{trading_pair}/orderbook/compact")
  BitcoindeCompactOrderbookWrapper getCompactOrderBook(
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest,
      @PathParam("trading_pair") String tradingPair)
      throws IOException;

  @GET
  @Path("{trading_pair}/orderbook")
  BitcoindeOrderbookWrapper getOrderBook(
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest,
      @PathParam("trading_pair") String tradingPair,
      @QueryParam("type") String type,
      @QueryParam("order_requirements_fullfilled") Integer orderRequirementsFullfilled,
      @QueryParam("only_kyc_full") Integer onlyKycFull,
      @QueryParam("only_express_orders") Integer onlyExpressOrders)
      throws IOException;

  @GET
  @Path("{trading_pair}/trades/history")
  BitcoindeTradesWrapper getTrades(
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest,
      @PathParam("trading_pair") String tradingPair,
      @QueryParam("since_tid") Integer since)
      throws IOException;

  @GET
  @Path("{trading_pair}/trades")
  BitcoindeMyTradesWrapper getMyTrades(
          @HeaderParam("X-API-KEY") String apiKey,
          @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
          @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest,
          @PathParam("trading_pair") String tradingPair,
          @QueryParam("type") String type,
          @QueryParam("state") Integer state,
          @QueryParam("only_trades_with_action_for_payment_or_transfer_required")
                  Integer only_trades_with_action_for_payment_or_transfer_required,
          @QueryParam("date_start") String date_start,
          @QueryParam("date_end") String date_end,
          @QueryParam("page") Integer page)
          throws BitcoindeException, IOException;

  @GET
  @Path("trades")
  BitcoindeMyTradesWrapper getMyTrades(
          @HeaderParam("X-API-KEY") String apiKey,
          @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
          @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest,
          @QueryParam("type") String type,
          @QueryParam("state") Integer state,
          @QueryParam("only_trades_with_action_for_payment_or_transfer_required")
                  Integer only_trades_with_action_for_payment_or_transfer_required,
          @QueryParam("date_start") String date_start,
          @QueryParam("date_end") String date_end,
          @QueryParam("page") Integer page)
          throws BitcoindeException, IOException;

  @GET
  @Path("account")
  BitcoindeAccountWrapper getAccount(
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest)
      throws BitcoindeException, IOException;

  @GET
  @Path("{currency}/account/ledger")
  BitcoindeAccountLedgerWrapper getAccountLedger(
          @HeaderParam("X-API-KEY") String apiKey,
          @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
          @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest,
          @PathParam("currency") String currency,
          @QueryParam("type") String type,
          @QueryParam("datetime_start") String datetime_start,
          @QueryParam("datetime_end") String datetime_end,
          @QueryParam("page") Integer page)
          throws BitcoindeException, IOException;

  @GET
  @Path("orders")
  BitcoindeMyOpenOrdersWrapper getMyOrders(
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest)
      throws IOException;
}
