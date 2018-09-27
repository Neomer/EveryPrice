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
    public class PriceManager : BaseEntityManager<PriceManager, IPrice>
    {
        protected PriceManager()
        {

        }

        public IList<IPrice> GetPricesByProduct(IProduct product)
        {
            return NHibernateHelper.Instance.CurrentSession
                .CreateCriteria<IPrice>()
                .Add(Expression.Eq("Product", product))
                .List<IPrice>(); 
        }
    }
}
