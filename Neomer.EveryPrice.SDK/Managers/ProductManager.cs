using Neomer.EveryPrice.SDK.Helpers;
using Neomer.EveryPrice.SDK.Models;
using NHibernate.Criterion;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Managers
{
    public class ProductManager : BaseEntityManager<ProductManager, IProduct>
    {
        protected ProductManager()
        {

        }

        public IList<Product> GetProductsByShop(IShop shop)
        {
            if (shop == null)
            {
                return null;
            }
            var query = NHibernateHelper.Instance.CurrentSession
                .CreateSQLQuery(String.Format(@"
                    SELECT prod.*, pr.* FROM 
	                    [EveryPrice].[dbo].[Products] prod
                    CROSS APPLY (
	                    SELECT  TOP 1 *
	                    FROM [EveryPrice].[dbo].[Prices] pr
	                    WHERE pr.ProductUid = prod.Uid
	                    ORDER BY pr.CreationDate DESC
                    ) pr
                    WHERE [prod].[ShopUid]='{0}'", shop.Uid))
                .AddEntity(typeof(Product))
                .AddEntity(typeof(Price));

            return query.List<Product>();
        }
    }
}
