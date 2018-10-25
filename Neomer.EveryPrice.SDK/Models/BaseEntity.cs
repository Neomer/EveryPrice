using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Models
{
    public class BaseEntity : IEntity
    {
        public virtual Guid Uid { get; set; }
    }
}
