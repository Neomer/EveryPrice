using Neomer.EveryPrice.SDK.Helpers;
using Neomer.EveryPrice.SDK.Models;
using Neomer.EveryPrice.SDK.Session;
using NHibernate;
using NHibernate.Criterion;
using System;
using System.Collections.Generic;
using System.Text.RegularExpressions;

namespace Neomer.EveryPrice.SDK.Managers
{
    public class BlobManager : BaseEntityManager<BlobManager, IBlob>
    {

        protected BlobManager()
        {

        }

        public IList<IBlob> FindByOwner(ISession session, IEntity uid)
        {
            throw new NotImplementedException();
        }
    }
}
