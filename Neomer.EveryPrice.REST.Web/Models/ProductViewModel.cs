using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Neomer.EveryPrice.REST.Web.Models
{
    public class ProductViewModel
    {
        public ProductViewModel()
        {

        }

        public ProductViewModel(IProduct product)
        {
            if (product ==  null)
            {
                return;
            }
            Uid = product.Uid;
            Name = product.Name;
            if (product.Prices == null)
            {
                Price = null;
            }
            else
            {
                var price = product.Prices.FirstOrDefault();
                if (price == null)
                {
                    Price = null;
                }
                else
                {
                    Price = new PriceViewModel(price);
                }
            }
        }

        public Guid Uid;

        public string Name;

        public PriceViewModel Price;

    }
}